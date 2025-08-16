package com.apps.gateway.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientAnalysisResponse {

    private String analysisId = UUID.randomUUID().toString();
    private String patientName;
    private TriageClassification triageClassification;
    private String preliminaryDiagnosis;
    private List<String> differentialDiagnoses;
    private List<String> clinicalRecommendations;
    private String urgencyLevel;
    private String timeRecommendation;
    private List<String> alertSigns;
    private String nextSteps;
    @JsonDeserialize(using = InstantFlexibleDeserializer.class)
    private Instant analysisDate = Instant.now();
    private double confidenceScore = 1.0;
    private String medicalDisclaimer = "Esta é uma avaliação preliminar para apoio à decisão clínica. O médico responsável deve sempre fazer a avaliação final e tomar as decisões terapêuticas.";

    // Getters e Setters
    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public TriageClassification getTriageClassification() {
        return triageClassification;
    }

    public void setTriageClassification(TriageClassification triageClassification) {
        this.triageClassification = triageClassification;
    }

    public String getPreliminaryDiagnosis() {
        return preliminaryDiagnosis;
    }

    public void setPreliminaryDiagnosis(String preliminaryDiagnosis) {
        this.preliminaryDiagnosis = preliminaryDiagnosis;
    }

    public List<String> getDifferentialDiagnoses() {
        return differentialDiagnoses;
    }

    public void setDifferentialDiagnoses(List<String> differentialDiagnoses) {
        this.differentialDiagnoses = differentialDiagnoses;
    }

    public List<String> getClinicalRecommendations() {
        return clinicalRecommendations;
    }

    public void setClinicalRecommendations(List<String> clinicalRecommendations) {
        this.clinicalRecommendations = clinicalRecommendations;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getTimeRecommendation() {
        return timeRecommendation;
    }

    public void setTimeRecommendation(String timeRecommendation) {
        this.timeRecommendation = timeRecommendation;
    }

    public List<String> getAlertSigns() {
        return alertSigns;
    }

    public void setAlertSigns(List<String> alertSigns) {
        this.alertSigns = alertSigns;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }

    public Instant getAnalysisDate() {
        return analysisDate;
    }

    public void setAnalysisDate(Instant analysisDate) {
        this.analysisDate = analysisDate;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getMedicalDisclaimer() {
        return medicalDisclaimer;
    }

    public void setMedicalDisclaimer(String medicalDisclaimer) {
        this.medicalDisclaimer = medicalDisclaimer;
    }
}