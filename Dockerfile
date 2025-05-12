# Use an official OpenJDK image as the base image for building
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and settings
COPY mvnw mvnw.cmd ./
COPY .mvn .mvn

# Copy the project files
COPY pom.xml ./
COPY src ./src

# Make the mvnw script executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests


# Use a smaller runtime image for the final stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
