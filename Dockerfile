# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# Stage 2: Create runtime environment
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/VehicleServiceManagement-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8086
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]
