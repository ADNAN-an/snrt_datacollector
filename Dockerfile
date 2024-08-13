FROM openjdk:17-jdk-slim

VOLUME /tmp

# Copy the local JAR file to the container
COPY target/* app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080

