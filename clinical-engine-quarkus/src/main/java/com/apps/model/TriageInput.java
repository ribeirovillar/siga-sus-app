package com.apps.model;

import java.time.LocalDateTime;
import java.util.List;

public class TriageInput {
    private String patientName;
    private int age;
    private String gender;
    private LocalDateTime arrivalTime;

    // Sinais vitais
    private Double temperature;
    private Integer heartRate;
    private Integer respiratoryRate;
    private Double bloodPressureSystolic;
    private Double bloodPressureDiastolic;
    private Double oxygenSaturation;
    private Double painScale;

    // Sintomas
    private String chiefComplaint;
    private String symptomDescription;
    private String symptomDuration;
    private boolean symptomProgressionWorsening;

    // Histórico
    private List<String> comorbidities;
    private List<String> currentMedications;
    private List<String> allergies;
    private boolean immunosuppressed;
    private Boolean pregnant;
    private Integer gestationalWeek;

    // Fatores de risco
    private boolean recentSurgery;
    private boolean anticoagulantUse;
    private boolean fever;
    private boolean alteredMentalStatus;
    private boolean chestPain;
    private boolean dyspnea;
    private boolean hypotension;
    private boolean tachycardia;
    private boolean bradycardia;
    private boolean lowOxygen;

    private String additionalNotes;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Double getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Double bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Double getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(Double bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public Double getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Double oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public Double getPainScale() {
        return painScale;
    }

    public void setPainScale(Double painScale) {
        this.painScale = painScale;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getSymptomDescription() {
        return symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription) {
        this.symptomDescription = symptomDescription;
    }

    public String getSymptomDuration() {
        return symptomDuration;
    }

    public void setSymptomDuration(String symptomDuration) {
        this.symptomDuration = symptomDuration;
    }

    public boolean isSymptomProgressionWorsening() {
        return symptomProgressionWorsening;
    }

    public void setSymptomProgressionWorsening(boolean symptomProgressionWorsening) {
        this.symptomProgressionWorsening = symptomProgressionWorsening;
    }

    public List<String> getComorbidities() {
        return comorbidities;
    }

    public void setComorbidities(List<String> comorbidities) {
        this.comorbidities = comorbidities;
    }

    public List<String> getCurrentMedications() {
        return currentMedications;
    }

    public void setCurrentMedications(List<String> currentMedications) {
        this.currentMedications = currentMedications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public boolean isImmunosuppressed() {
        return immunosuppressed;
    }

    public void setImmunosuppressed(boolean immunosuppressed) {
        this.immunosuppressed = immunosuppressed;
    }

    public Boolean getPregnant() {
        return pregnant;
    }

    public void setPregnant(Boolean pregnant) {
        this.pregnant = pregnant;
    }

    public Integer getGestationalWeek() {
        return gestationalWeek;
    }

    public void setGestationalWeek(Integer gestationalWeek) {
        this.gestationalWeek = gestationalWeek;
    }

    public boolean isRecentSurgery() {
        return recentSurgery;
    }

    public void setRecentSurgery(boolean recentSurgery) {
        this.recentSurgery = recentSurgery;
    }

    public boolean isAnticoagulantUse() {
        return anticoagulantUse;
    }

    public void setAnticoagulantUse(boolean anticoagulantUse) {
        this.anticoagulantUse = anticoagulantUse;
    }

    public boolean isFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public boolean isAlteredMentalStatus() {
        return alteredMentalStatus;
    }

    public void setAlteredMentalStatus(boolean alteredMentalStatus) {
        this.alteredMentalStatus = alteredMentalStatus;
    }

    public boolean isChestPain() {
        return chestPain;
    }

    public void setChestPain(boolean chestPain) {
        this.chestPain = chestPain;
    }

    public boolean isDyspnea() {
        return dyspnea;
    }

    public void setDyspnea(boolean dyspnea) {
        this.dyspnea = dyspnea;
    }

    public boolean isHypotension() {
        return hypotension;
    }

    public void setHypotension(boolean hypotension) {
        this.hypotension = hypotension;
    }

    public boolean isTachycardia() {
        return tachycardia;
    }

    public void setTachycardia(boolean tachycardia) {
        this.tachycardia = tachycardia;
    }

    public boolean isBradycardia() {
        return bradycardia;
    }

    public void setBradycardia(boolean bradycardia) {
        this.bradycardia = bradycardia;
    }

    public boolean isLowOxygen() {
        return lowOxygen;
    }

    public void setLowOxygen(boolean lowOxygen) {
        this.lowOxygen = lowOxygen;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}