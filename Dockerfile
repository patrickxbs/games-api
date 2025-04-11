FROM openjdk:17-jdk-slim

COPY target/games-api-0.0.1-SNAPSHOT.jar app/app.jar

CMD ["java", "-jar", "app/app.jar"]