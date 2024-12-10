# Use a lightweight OpenJDK image for Java 17
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY src/main/java/com/example/festquestbackend/jar/backend.jar /app/backend.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "backend.jar"]
