package com.apps.controller;

import com.apps.model.TriageInput;
import com.apps.parameter.TriageInputParameter;
import com.apps.presenter.TriageOutputPresenter;
import com.apps.service.TriageOrchestrationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/triage")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TriageController {

    @Inject
    TriageOrchestrationService triageService;

    @POST
    public Response analyze(TriageInputParameter param) {
        try {
            TriageInput input = mapToModel(param);
            TriageOutputPresenter result = triageService.processTriage(input);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(500)
                .entity("Erro ao processar triagem: " + e.getMessage())
                .build();
        }
    }

    private TriageInput mapToModel(TriageInputParameter param) {
        TriageInput input = new TriageInput();
        input.setPatientName(param.patientName);
        input.setAge(param.age);
        input.setGender(param.gender);
        input.setArrivalTime(param.arrivalTime != null ? param.arrivalTime : java.time.LocalDateTime.now());

        input.setTemperature(param.temperature);
        input.setHeartRate(param.heartRate);
        input.setRespiratoryRate(param.respiratoryRate);
        input.setBloodPressureSystolic(param.bloodPressureSystolic);
        input.setBloodPressureDiastolic(param.bloodPressureDiastolic);
        input.setOxygenSaturation(param.oxygenSaturation);
        input.setPainScale(param.painScale);

        input.setChiefComplaint(param.chiefComplaint);
        input.setSymptomDescription(param.symptomDescription);
        input.setSymptomDuration(param.symptomDuration);
        input.setSymptomProgressionWorsening(param.symptomProgressionWorsening);

        input.setComorbidities(param.comorbidities);
        input.setCurrentMedications(param.currentMedications);
        input.setAllergies(param.allergies);

        input.setImmunosuppressed(param.immunosuppressed);
        input.setPregnant(param.pregnant);
        input.setGestationalWeek(param.gestationalWeek);

        input.setRecentSurgery(param.recentSurgery);
        input.setAnticoagulantUse(param.anticoagulantUse);

        input.setAdditionalNotes(param.additionalNotes);

        // Preenche sinais de risco automaticamente
        if (param.temperature != null) input.setFever(param.temperature > 38.0);
        if (param.heartRate != null) input.setTachycardia(param.heartRate > 100);
        if (param.bloodPressureSystolic != null) input.setHypotension(param.bloodPressureSystolic < 90);
        if (param.oxygenSaturation != null) input.setLowOxygen(param.oxygenSaturation < 94);

        return input;
    }
}