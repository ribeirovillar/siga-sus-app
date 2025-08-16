package com.app.rules;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/rules")
@Tag(name = "Clinical Rules", description = "Análise clínica com IA")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClinicalRuleResource {

    private final ClinicalAnalysisService analysisService;

    public ClinicalRuleResource(ClinicalAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @POST
    @Path("/analyze")
    @Operation(summary = "Analisa dados clínicos", description = "Recebe dados clínicos e retorna análise gerada pela IA")
    public Response analyzeClinicalData(ClinicalDataRequest request) {
        try {
            ClinicalAnalysisResponse analysis = analysisService.analyze(request);
            return Response.ok(analysis).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro na análise clínica: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/health")
    @Operation(summary = "Health check", description = "Verifica se o serviço está funcionando")
    public Response health() {
        return Response.ok(new HealthResponse("Clinical Rule Engine is running")).build();
    }
}
