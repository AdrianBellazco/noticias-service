FROM openjdk:8-jdk-alpine
LABEL authors="adrian"

ENTRYPOINT ["top", "-b"]