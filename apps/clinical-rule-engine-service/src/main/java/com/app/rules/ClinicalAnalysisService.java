package com.app.rules;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class ClinicalAnalysisService {

    private static final Logger LOG = Logger.getLogger(ClinicalAnalysisService.class);

    public ClinicalAnalysisResponse analyze(ClinicalDataRequest request) {
        LOG.infof("Iniciando análise clínica para paciente: %s", request.getPatientId());

        ClinicalAnalysisResponse response = new ClinicalAnalysisResponse();
        response.setAnalysisId(UUID.randomUUID().toString());
        response.setPatientId(request.getPatientId());

        // Análise de sintomas
        SymptomAnalysis symptomAnalysis = analyzeSymptoms(request.getSymptoms());

        // Análise de sinais vitais
        VitalSignsAnalysis vitalAnalysis = analyzeVitalSigns(request.getVitalSigns());

        // Combinar análises para determinar nível de risco
        RiskAssessment riskAssessment = assessRisk(symptomAnalysis, vitalAnalysis, request.getMedicalHistory());

        // Gerar diagnósticos potenciais
        List<ClinicalAnalysisResponse.PotentialDiagnosis> diagnoses = generatePotentialDiagnoses(symptomAnalysis, request);

        // Gerar recomendações
        List<String> recommendations = generateRecommendations(riskAssessment, symptomAnalysis);

        // Gerar próximas perguntas para triagem
        List<String> nextQuestions = generateNextQuestions(symptomAnalysis, request);

        // Montar resposta
        response.setRiskLevel(riskAssessment.getRiskLevel());
        response.setUrgencyScore(riskAssessment.getUrgencyScore());
        response.setPotentialDiagnoses(diagnoses);
        response.setRecommendedActions(recommendations);
        response.setNextQuestions(nextQuestions);
        response.setAiConfidence(calculateConfidence(symptomAnalysis, vitalAnalysis));
        response.setAdditionalNotes(generateAdditionalNotes(riskAssessment));

        LOG.infof("Análise concluída - Nível de risco: %s, Score: %d",
                response.getRiskLevel(), response.getUrgencyScore());

        return response;
    }

    private SymptomAnalysis analyzeSymptoms(List<String> symptoms) {
        if (symptoms == null || symptoms.isEmpty()) {
            return new SymptomAnalysis(Collections.emptyMap(), 0);
        }

        Map<String, Integer> severityScores = new HashMap<>();
        int totalSeverity = 0;

        for (String symptom : symptoms) {
            String normalizedSymptom = symptom.toLowerCase().trim();
            int severity = calculateSymptomSeverity(normalizedSymptom);
            severityScores.put(normalizedSymptom, severity);
            totalSeverity += severity;
        }

        return new SymptomAnalysis(severityScores, totalSeverity);
    }

    private int calculateSymptomSeverity(String symptom) {
        // Sintomas críticos (score 8-10)
        if (symptom.contains("dor no peito") || symptom.contains("falta de ar severa") ||
                symptom.contains("perda de consciência") || symptom.contains("convulsão")) {
            return 10;
        }

        // Sintomas graves (score 6-7)
        if (symptom.contains("febre alta") || symptom.contains("vômito") ||
                symptom.contains("dor abdominal intensa")) {
            return 7;
        }

        // Sintomas moderados (score 4-5)
        if (symptom.contains("febre") || symptom.contains("dor de cabeça") ||
                symptom.contains("tosse")) {
            return 5;
        }

        // Sintomas leves (score 1-3)
        return 3;
    }

    private VitalSignsAnalysis analyzeVitalSigns(ClinicalDataRequest.VitalSigns vitalSigns) {
        if (vitalSigns == null) {
            return new VitalSignsAnalysis(false, 0);
        }

        boolean isAbnormal = false;
        int abnormalityScore = 0;

        // Análise da frequência cardíaca
        if (vitalSigns.getHeartRate() != null) {
            int hr = vitalSigns.getHeartRate();
            if (hr < 60 || hr > 100) {
                isAbnormal = true;
                abnormalityScore += (hr < 50 || hr > 120) ? 3 : 1;
            }
        }

        // Análise da temperatura
        if (vitalSigns.getTemperature() != null) {
            double temp = vitalSigns.getTemperature();
            if (temp > 37.5 || temp < 35.0) {
                isAbnormal = true;
                abnormalityScore += (temp > 39.0 || temp < 34.0) ? 3 : 2;
            }
        }

        // Análise da saturação de oxigênio
        if (vitalSigns.getOxygenSaturation() != null) {
            int spo2 = vitalSigns.getOxygenSaturation();
            if (spo2 < 95) {
                isAbnormal = true;
                abnormalityScore += (spo2 < 90) ? 4 : 2;
            }
        }

        return new VitalSignsAnalysis(isAbnormal, abnormalityScore);
    }

    private RiskAssessment assessRisk(SymptomAnalysis symptomAnalysis, VitalSignsAnalysis vitalAnalysis,
                                      List<String> medicalHistory) {
        int totalScore = symptomAnalysis.getTotalSeverity() + vitalAnalysis.getAbnormalityScore();

        // Ajustar score baseado no histórico médico
        if (medicalHistory != null && !medicalHistory.isEmpty()) {
            for (String condition : medicalHistory) {
                if (condition.toLowerCase().contains("diabetes") ||
                        condition.toLowerCase().contains("hipertensão") ||
                        condition.toLowerCase().contains("cardiopatia")) {
                    totalScore += 2;
                }
            }
        }

        ClinicalAnalysisResponse.RiskLevel riskLevel;
        if (totalScore >= 15) {
            riskLevel = ClinicalAnalysisResponse.RiskLevel.CRITICAL;
        } else if (totalScore >= 10) {
            riskLevel = ClinicalAnalysisResponse.RiskLevel.HIGH;
        } else if (totalScore >= 5) {
            riskLevel = ClinicalAnalysisResponse.RiskLevel.MEDIUM;
        } else {
            riskLevel = ClinicalAnalysisResponse.RiskLevel.LOW;
        }

        return new RiskAssessment(riskLevel, totalScore);
    }

    private List<ClinicalAnalysisResponse.PotentialDiagnosis> generatePotentialDiagnoses(
            SymptomAnalysis symptomAnalysis, ClinicalDataRequest request) {

        List<ClinicalAnalysisResponse.PotentialDiagnosis> diagnoses = new ArrayList<>();

        // Lógica simplificada de diagnóstico baseada em sintomas
        Map<String, Integer> symptoms = symptomAnalysis.getSeverityScores();

        if (symptoms.containsKey("febre") && symptoms.containsKey("tosse")) {
            diagnoses.add(new ClinicalAnalysisResponse.PotentialDiagnosis("Infecção respiratória", 0.75));
        }

        if (symptoms.containsKey("dor no peito")) {
            diagnoses.add(new ClinicalAnalysisResponse.PotentialDiagnosis("Síndrome coronariana aguda", 0.60));
        }

        if (symptoms.containsKey("dor de cabeça") && symptoms.containsKey("febre")) {
            diagnoses.add(new ClinicalAnalysisResponse.PotentialDiagnosis("Cefaleia secundária", 0.65));
        }

        return diagnoses;
    }

    private List<String> generateRecommendations(RiskAssessment riskAssessment, SymptomAnalysis symptomAnalysis) {
        List<String> recommendations = new ArrayList<>();

        switch (riskAssessment.getRiskLevel()) {
            case CRITICAL:
                recommendations.add("Atendimento médico imediato - emergência");
                recommendations.add("Monitorização contínua dos sinais vitais");
                break;
            case HIGH:
                recommendations.add("Atendimento médico urgente");
                recommendations.add("Reavaliação em 2-4 horas");
                break;
            case MEDIUM:
                recommendations.add("Atendimento médico em até 24 horas");
                recommendations.add("Monitorar evolução dos sintomas");
                break;
            case LOW:
                recommendations.add("Cuidados domiciliares");
                recommendations.add("Retornar se sintomas piorarem");
                break;
        }

        return recommendations;
    }

    private List<String> generateNextQuestions(SymptomAnalysis symptomAnalysis, ClinicalDataRequest request) {
        List<String> questions = new ArrayList<>();

        if (symptomAnalysis.getSeverityScores().containsKey("dor")) {
            questions.add("Qual a intensidade da dor numa escala de 1 a 10?");
            questions.add("A dor irradia para outras partes do corpo?");
        }

        if (symptomAnalysis.getSeverityScores().containsKey("febre")) {
            questions.add("Há quanto tempo tem febre?");
            questions.add("Apresenta calafrios ou sudorese?");
        }

        questions.add("Tomou algum medicamento para os sintomas?");
        questions.add("Os sintomas estão melhorando ou piorando?");

        return questions;
    }

    private Double calculateConfidence(SymptomAnalysis symptomAnalysis, VitalSignsAnalysis vitalAnalysis) {
        double confidence = 0.7; // Base confidence

        if (!symptomAnalysis.getSeverityScores().isEmpty()) {
            confidence += 0.2;
        }

        if (vitalAnalysis.isAbnormal()) {
            confidence += 0.1;
        }

        return Math.min(confidence, 1.0);
    }

    private String generateAdditionalNotes(RiskAssessment riskAssessment) {
        return String.format("Análise baseada em algoritmo de triagem automatizada. " +
                        "Score de urgência: %d. Recomenda-se sempre avaliação médica presencial.",
                riskAssessment.getUrgencyScore());
    }

    // Classes auxiliares internas
    private static class SymptomAnalysis {
        private final Map<String, Integer> severityScores;
        private final int totalSeverity;

        public SymptomAnalysis(Map<String, Integer> severityScores, int totalSeverity) {
            this.severityScores = severityScores;
            this.totalSeverity = totalSeverity;
        }

        public Map<String, Integer> getSeverityScores() {
            return severityScores;
        }

        public int getTotalSeverity() {
            return totalSeverity;
        }
    }

    private static class VitalSignsAnalysis {
        private final boolean abnormal;
        private final int abnormalityScore;

        public VitalSignsAnalysis(boolean abnormal, int abnormalityScore) {
            this.abnormal = abnormal;
            this.abnormalityScore = abnormalityScore;
        }

        public boolean isAbnormal() {
            return abnormal;
        }

        public int getAbnormalityScore() {
            return abnormalityScore;
        }
    }

    private static class RiskAssessment {
        private final ClinicalAnalysisResponse.RiskLevel riskLevel;
        private final int urgencyScore;

        public RiskAssessment(ClinicalAnalysisResponse.RiskLevel riskLevel, int urgencyScore) {
            this.riskLevel = riskLevel;
            this.urgencyScore = urgencyScore;
        }

        public ClinicalAnalysisResponse.RiskLevel getRiskLevel() {
            return riskLevel;
        }

        public int getUrgencyScore() {
            return urgencyScore;
        }
    }
}
