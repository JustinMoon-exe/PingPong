FROM openjdk:8-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:8-jre-slim
COPY --from=builder /app/build/libs/service1-*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]