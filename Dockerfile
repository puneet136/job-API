# Stage 1: Build JAR with Maven
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

# Copy pom first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source
COPY src ./src

# Build (skip tests for faster deploys)
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime
FROM eclipse-temurin:17-jre-alpine

# Non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy JAR from build stage
COPY --from=builder /build/target/*.jar app.jar

# Set permissions
RUN chown -R appuser:appgroup /app

USER appuser:appgroup

# Render uses $PORT env var
EXPOSE ${PORT:-8080}

ENTRYPOINT ["java", "-jar", "app.jar"]