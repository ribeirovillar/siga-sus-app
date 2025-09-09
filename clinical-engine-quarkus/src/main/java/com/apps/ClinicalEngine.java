package com.apps;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@QuarkusMain
@Path("/hello")
public class ClinicalEngine implements QuarkusApplication {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @Override
    public int run(String... args) throws Exception {
        System.out.println("🏥 Clinical Rule Engine Service iniciado com sucesso!");
        System.out.println("📊 API disponível em: http://localhost:8082/api/clinical");
        System.out.println("🔍 Swagger UI: http://localhost:8082/swagger-ui");
        System.out.println("❤️ Health Check: http://localhost:8082/api/clinical/health");

        Quarkus.waitForExit();
        return 0;
    }

    public static void main(String... args) {
        Quarkus.run(ClinicalEngine.class, args);
    }
}
