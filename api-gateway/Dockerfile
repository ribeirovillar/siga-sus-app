FROM maven:3.9.11-eclipse-temurin-24 as build
WORKDIR /app
RUN apt-get update && apt-get install -y protobuf-compiler
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]