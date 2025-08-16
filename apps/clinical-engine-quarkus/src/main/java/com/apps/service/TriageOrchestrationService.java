package com.apps.service;


import com.apps.gateway.ClinicalAiGateway;
import com.apps.gateway.dto.PatientAnalysisResponse;
import com.apps.model.TriageInput;
import com.apps.presenter.TriageOutputPresenter;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;

@ApplicationScoped
public class TriageOrchestrationService {

    private final ClinicalAiGateway clinicalAiGateway;

    public TriageOrchestrationService(ClinicalAiGateway clinicalAiGateway) {
        this.clinicalAiGateway = clinicalAiGateway;
    }

    public TriageOutputPresenter processTriage(TriageInput input) {
        // Monta histórico médico
        String medicalHistory = String.join(", ", input.getComorbidities() != null ? input.getComorbidities() : java.util.Collections.emptyList());
        String medications = String.join(", ", input.getCurrentMedications() != null ? input.getCurrentMedications() : java.util.Collections.emptyList());
        String allergies = String.join(", ", input.getAllergies() != null ? input.getAllergies() : java.util.Collections.emptyList());

        String symptoms = String.format("%s. %s. Duração: %s. %s",
            input.getChiefComplaint(),
            input.getSymptomDescription(),
            input.getSymptomDuration(),
            input.isSymptomProgressionWorsening() ? "Os sintomas estão piorando." : ""
        );

        // Chama o gateway de IA
        PatientAnalysisResponse response = clinicalAiGateway.analyzePatient(
            input.getPatientName(),
            input.getAge(),
            input.getGender(),
            medicalHistory,
            medications,
            allergies,
            input.isImmunosuppressed(),
            input.getPregnant(),
            input.getGestationalWeek(),
            input.getTemperature(),
            input.getHeartRate(),
            input.getRespiratoryRate(),
            input.getBloodPressureSystolic(),
            input.getBloodPressureDiastolic(),
            input.getOxygenSaturation(),
            input.getPainScale(),
            input.getChiefComplaint(),
            input.getSymptomDescription(),
            input.getSymptomDuration(),
            input.isSymptomProgressionWorsening(),
            symptoms,
            input.isRecentSurgery(),
            input.isAnticoagulantUse(),
            input.isFever(),
            input.isAlteredMentalStatus(),
            input.isChestPain(),
            input.isDyspnea(),
            input.isHypotension(),
            input.isTachycardia(),
            input.isBradycardia(),
            input.isLowOxygen(),
            input.getAdditionalNotes()
        );

        // Mapeia para o presenter
        return mapToPresenter(response);
    }

    private TriageOutputPresenter mapToPresenter(PatientAnalysisResponse response) {
        TriageOutputPresenter presenter = new TriageOutputPresenter();
        presenter.analysisId = response.getAnalysisId();
        presenter.patientName = response.getPatientName();
        presenter.preliminaryDiagnosis = response.getPreliminaryDiagnosis();
        presenter.differentialDiagnoses = response.getDifferentialDiagnoses();
        presenter.clinicalRecommendations = response.getClinicalRecommendations();
        presenter.urgencyLevel = response.getUrgencyLevel();
        presenter.timeRecommendation = response.getTimeRecommendation();
        presenter.alertSigns = response.getAlertSigns();
        presenter.nextSteps = response.getNextSteps();
        presenter.analysisDate = Instant.from(response.getAnalysisDate());
        presenter.confidenceScore = response.getConfidenceScore();
        presenter.medicalDisclaimer = response.getMedicalDisclaimer();

        // Classificação
        presenter.classification = response.getTriageClassification();

        return presenter;
    }
}