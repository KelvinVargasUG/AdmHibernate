FROM adoptopenjdk:11-jdk-hotspot
MAINTAINER kelvinVargas

RUN groupadd -r spring && useradd -r -g spring spring

USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} AdmUser.jar

ENTRYPOINT ["java", "-jar", "/AdmUser.jar"]