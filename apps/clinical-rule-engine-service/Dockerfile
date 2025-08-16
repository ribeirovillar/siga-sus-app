# Multi-stage build for Quarkus application
FROM maven:3.9.4-openjdk-21 AS build
WORKDIR /app

# Copy parent pom and dependencies
COPY ../../pom.xml ./
COPY ../../common ./common/

# Copy service specific files
COPY pom.xml ./apps/clinical-rule-engine-service/
COPY src ./apps/clinical-rule-engine-service/src/

# Build the Quarkus application
WORKDIR /app/apps/clinical-rule-engine-service
RUN mvn clean package -DskipTests

# Runtime stage with optimized Quarkus runner
FROM registry.access.redhat.com/ubi8/openjdk-21-runtime:latest
WORKDIR /app

# Install curl for health checks
USER root
RUN microdnf install curl && microdnf clean all

# Copy the Quarkus runner
COPY --from=build /app/apps/clinical-rule-engine-service/target/quarkus-app/lib/ /app/lib/
COPY --from=build /app/apps/clinical-rule-engine-service/target/quarkus-app/*.jar /app/
COPY --from=build /app/apps/clinical-rule-engine-service/target/quarkus-app/app/ /app/app/
COPY --from=build /app/apps/clinical-rule-engine-service/target/quarkus-app/quarkus/ /app/quarkus/

# Change back to non-root user
USER 185

# Expose ports
EXPOSE 8084

# Health check for Quarkus
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8084/api/health || exit 1

# Quarkus startup command
ENTRYPOINT ["java", "-jar", "/app/quarkus-run.jar"]
