FROM openjdk:11.0.2-jdk-slim

EXPOSE 8080 8081
ADD build/libs/*.jar /opt/service/service.jar
WORKDIR /opt/service

ENTRYPOINT exec java -Xms256M -Xmx256M -jar service.jar
