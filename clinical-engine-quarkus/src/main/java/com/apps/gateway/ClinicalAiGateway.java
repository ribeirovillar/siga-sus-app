package com.apps.gateway;


import com.apps.gateway.dto.PatientAnalysisResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClinicalAiGateway {

    private final ClinicalAiService clinicalAiService;

    public ClinicalAiGateway(ClinicalAiService clinicalAiService) {
        this.clinicalAiService = clinicalAiService;
    }

    public PatientAnalysisResponse analyzePatient(String name,
                                                  int age,
                                                  String gender,
                                                  String medicalHistory,
                                                  String medications,
                                                  String allergies,
                                                  boolean immunosuppressed,
                                                  Boolean pregnant,
                                                  Integer gestationalWeek,
                                                  Double temperature,
                                                  Integer heartRate,
                                                  Integer respiratoryRate,
                                                  Double bloodPressureSystolic,
                                                  Double bloodPressureDiastolic,
                                                  Double oxygenSaturation,
                                                  Double painScale,
                                                  String chiefComplaint,
                                                  String symptomDescription,
                                                  String symptomDuration,
                                                  boolean symptomProgressionWorsening,
                                                  String symptoms,
                                                  boolean recentSurgery,
                                                  boolean anticoagulantUse,
                                                  boolean fever,
                                                  boolean alteredMentalStatus,
                                                  boolean chestPain,
                                                  boolean dyspnea,
                                                  boolean hypotension,
                                                  boolean tachycardia,
                                                  boolean bradycardia,
                                                  boolean lowOxygen,
                                                  String additionalNotes) {
        return clinicalAiService.analyzePatient(name, age, gender, medicalHistory, medications, allergies,
            immunosuppressed, pregnant, gestationalWeek, temperature, heartRate, respiratoryRate,
            bloodPressureSystolic, bloodPressureDiastolic, oxygenSaturation, painScale, chiefComplaint,
            symptomDescription, symptomDuration, symptomProgressionWorsening, symptoms, recentSurgery,
            anticoagulantUse, fever, alteredMentalStatus, chestPain, dyspnea, hypotension, tachycardia,
            bradycardia, lowOxygen, additionalNotes);
    }
}