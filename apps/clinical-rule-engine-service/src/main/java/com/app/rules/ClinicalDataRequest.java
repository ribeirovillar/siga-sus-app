package com.app.rules;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class ClinicalDataRequest {

    @NotNull
    @NotEmpty
    @JsonProperty("patient_id")
    private String patientId;

    @NotNull
    @JsonProperty("symptoms")
    private List<String> symptoms;

    @JsonProperty("vital_signs")
    private VitalSigns vitalSigns;

    @JsonProperty("medical_history")
    private List<String> medicalHistory;

    @JsonProperty("medications")
    private List<String> medications;

    @JsonProperty("additional_data")
    private Map<String, Object> additionalData;

    // Constructors
    public ClinicalDataRequest() {}

    public ClinicalDataRequest(String patientId, List<String> symptoms) {
        this.patientId = patientId;
        this.symptoms = symptoms;
    }

    // Getters and Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public List<String> getSymptoms() { return symptoms; }
    public void setSymptoms(List<String> symptoms) { this.symptoms = symptoms; }

    public VitalSigns getVitalSigns() { return vitalSigns; }
    public void setVitalSigns(VitalSigns vitalSigns) { this.vitalSigns = vitalSigns; }

    public List<String> getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(List<String> medicalHistory) { this.medicalHistory = medicalHistory; }

    public List<String> getMedications() { return medications; }
    public void setMedications(List<String> medications) { this.medications = medications; }

    public Map<String, Object> getAdditionalData() { return additionalData; }
    public void setAdditionalData(Map<String, Object> additionalData) { this.additionalData = additionalData; }

    // Nested class for vital signs
    public static class VitalSigns {
        @JsonProperty("blood_pressure")
        private String bloodPressure;

        @JsonProperty("heart_rate")
        private Integer heartRate;

        @JsonProperty("temperature")
        private Double temperature;

        @JsonProperty("oxygen_saturation")
        private Integer oxygenSaturation;

        // Constructors
        public VitalSigns() {}

        // Getters and Setters
        public String getBloodPressure() { return bloodPressure; }
        public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

        public Integer getHeartRate() { return heartRate; }
        public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

        public Double getTemperature() { return temperature; }
        public void setTemperature(Double temperature) { this.temperature = temperature; }

        public Integer getOxygenSaturation() { return oxygenSaturation; }
        public void setOxygenSaturation(Integer oxygenSaturation) { this.oxygenSaturation = oxygenSaturation; }
    }
}
