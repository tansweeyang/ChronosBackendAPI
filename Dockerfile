FROM openjdk:22
MAINTAINER eislyn.dev
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
