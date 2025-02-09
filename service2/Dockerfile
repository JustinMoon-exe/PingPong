# Stage 1: Build the application
FROM openjdk:8-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build

# Stage 2: Run the application
FROM openjdk:8-jre-slim
WORKDIR /app
# Copy the JAR file from the builder stage
COPY --from=builder /app/build/libs/service2.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]