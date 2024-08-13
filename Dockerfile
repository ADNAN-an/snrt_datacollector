FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/datacollector-0.0.1-SNAPSHOT.jar /app/datacollector.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "datacollector.jar"]

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/snrt_datacollector
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=159951
