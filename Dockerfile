#FROM ubuntu:latest
#LABEL authors="Akash"
#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#COPY target/Operations-0.0.1-SNAPSHOT.jar app.jar
#
##ENTRYPOINT["java","-jar","sb_docker_app.jar"]
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
# Stage 1: Build the JAR file
# Stage 1: Build the JAR file
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a smaller image for running the application
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/Operations-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

