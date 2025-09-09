FROM eclipse-temurin:24-jdk as build
WORKDIR /app
RUN apt-get update && apt-get install -y protobuf-compiler
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]