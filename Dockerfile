FROM openjdk:17-jdk-alpine
LABEL authors="adrian"

#ARG JAR_FILE=/target/*.jar
#COPY ${JAR_FILE} noticias-service-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY target/noticias-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]