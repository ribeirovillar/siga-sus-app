package com.app.rules;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class ClinicalAnalysisResponse {

    @JsonProperty("analysis_id")
    private String analysisId;

    @JsonProperty("patient_id")
    private String patientId;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("risk_level")
    private RiskLevel riskLevel;

    @JsonProperty("urgency_score")
    private Integer urgencyScore;

    @JsonProperty("recommended_actions")
    private List<String> recommendedActions;

    @JsonProperty("potential_diagnoses")
    private List<PotentialDiagnosis> potentialDiagnoses;

    @JsonProperty("next_questions")
    private List<String> nextQuestions;

    @JsonProperty("ai_confidence")
    private Double aiConfidence;

    @JsonProperty("additional_notes")
    private String additionalNotes;

    // Constructors
    public ClinicalAnalysisResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getUrgencyScore() {
        return urgencyScore;
    }

    public void setUrgencyScore(Integer urgencyScore) {
        this.urgencyScore = urgencyScore;
    }

    public List<String> getRecommendedActions() {
        return recommendedActions;
    }

    public void setRecommendedActions(List<String> recommendedActions) {
        this.recommendedActions = recommendedActions;
    }

    public List<PotentialDiagnosis> getPotentialDiagnoses() {
        return potentialDiagnoses;
    }

    public void setPotentialDiagnoses(List<PotentialDiagnosis> potentialDiagnoses) {
        this.potentialDiagnoses = potentialDiagnoses;
    }

    public List<String> getNextQuestions() {
        return nextQuestions;
    }

    public void setNextQuestions(List<String> nextQuestions) {
        this.nextQuestions = nextQuestions;
    }

    public Double getAiConfidence() {
        return aiConfidence;
    }

    public void setAiConfidence(Double aiConfidence) {
        this.aiConfidence = aiConfidence;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    // Enums and nested classes
    public enum RiskLevel {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public static class PotentialDiagnosis {
        @JsonProperty("diagnosis")
        private String diagnosis;

        @JsonProperty("probability")
        private Double probability;

        @JsonProperty("icd_code")
        private String icdCode;

        public PotentialDiagnosis() {
        }

        public PotentialDiagnosis(String diagnosis, Double probability) {
            this.diagnosis = diagnosis;
            this.probability = probability;
        }

        // Getters and Setters
        public String getDiagnosis() {
            return diagnosis;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public Double getProbability() {
            return probability;
        }

        public void setProbability(Double probability) {
            this.probability = probability;
        }

        public String getIcdCode() {
            return icdCode;
        }

        public void setIcdCode(String icdCode) {
            this.icdCode = icdCode;
        }
    }
}
