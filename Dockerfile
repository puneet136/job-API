# Stage 1: Build JAR with Maven + JDK 21
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Copy pom.xml first → caches dependencies for faster rebuilds
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the JAR (skip tests to speed up deploys on Render)
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime with Java 21 JRE (matches compile version)
FROM eclipse-temurin:21-jre-alpine

# Create non-root user for better security (recommended)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /build/target/*.jar app.jar

# Set correct permissions
RUN chown -R appuser:appgroup /app

# Run as non-root
USER appuser:appgroup

# Expose the port Render assigns via $PORT (fallback to 8080)
EXPOSE ${PORT:-8080}

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]