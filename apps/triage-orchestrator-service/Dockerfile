# Build stage
FROM maven:3.9.4-openjdk-21 AS build
WORKDIR /app

# Copy parent pom first for dependency resolution
COPY ../../pom.xml ./
COPY ../pom.xml ./apps/
COPY ../../common ./common/

# Copy the service specific files
COPY pom.xml ./apps/triage-orchestrator-service/
COPY src ./apps/triage-orchestrator-service/src/

# Build the application
RUN mvn clean package -pl apps/triage-orchestrator-service -am -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built JAR
COPY --from=build /app/apps/triage-orchestrator-service/target/*.jar app.jar

# Create non-root user
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown appuser:appuser app.jar
USER appuser

# Expose ports (HTTP and gRPC)
EXPOSE 8083 9090

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8083/api/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
