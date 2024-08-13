# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the local JAR file to the container
COPY target/datacollector-0.0.1-SNAPSHOT.jar /app/datacollector.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "datacollector.jar"]
