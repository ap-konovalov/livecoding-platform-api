FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle/ ./gradle/
COPY gradlew ./
COPY src ./src
RUN chmod +x ./gradlew \
&& ./gradlew clean build --no-daemon

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/livecoding-api-1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]