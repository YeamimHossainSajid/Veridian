FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=3s --retries=3 --start-period=20s \
  CMD wget -qO- http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-XX:+UseZGC", "-XX:+AlwaysPreTouch", "-XX:MaxGCPauseMillis=5", "-XX:MaxRAMPercentage=80.0", "-jar", "app.jar"]
