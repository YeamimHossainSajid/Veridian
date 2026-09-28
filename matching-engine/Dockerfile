FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-XX:+UseZGC", "-XX:+AlwaysPreTouch", "-XX:MaxGCPauseMillis=5", "-XX:MaxRAMPercentage=80.0", "-jar", "app.jar"]
