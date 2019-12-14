FROM maven:3.6.3-jdk-8 AS build
COPY ./pom.xml /usr
RUN mvn -f /usr dependency:go-offline -B
COPY ./src /usr/src
RUN mvn -f /usr clean package

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/target/Grafana_ThinkSpeak_Datasource-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
