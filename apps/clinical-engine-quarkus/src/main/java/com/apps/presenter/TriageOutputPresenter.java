package com.apps.presenter;

import com.apps.gateway.dto.TriageClassification;

import java.time.Instant;
import java.util.List;

public class TriageOutputPresenter {
    public String analysisId;
    public String patientName;
    public TriageClassification classification;
    public String preliminaryDiagnosis;
    public List<String> differentialDiagnoses;
    public List<String> clinicalRecommendations;
    public String urgencyLevel;
    public String timeRecommendation;
    public List<String> alertSigns;
    public String nextSteps;
    public Instant analysisDate;
    public double confidenceScore;
    public String medicalDisclaimer;


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

    public TriageClassification getClassification() {
        return classification;
    }

    public void setClassification(TriageClassification classification) {
        this.classification = classification;
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