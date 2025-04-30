# -------- Stage 1: Build the application --------
    FROM maven:3.9.6-eclipse-temurin-21 AS builder

    # Set working directory
    WORKDIR /app
    
    # Copy the Maven project files first to leverage caching
    COPY pom.xml ./
    COPY .mvn .mvn
    COPY mvnw ./
    
    # Download dependencies (improves caching)
    RUN ./mvnw dependency:go-offline
    
    # Copy the rest of the source code
    COPY src ./src
    
    # Build the application (skip tests for faster build)
    RUN ./mvnw clean package -DskipTests
    
    # -------- Stage 2: Create a lightweight runtime image --------
    FROM eclipse-temurin:21-jre-alpine
    
    # Set working directory
    WORKDIR /app
    
    # Copy the built JAR from the builder stage
    COPY --from=builder /app/target/expenseTracker-0.0.1-SNAPSHOT.jar ./expenseTracker.jar
    
    # Expose the application port
    EXPOSE 8080
    
    # Run the Spring Boot application
    ENTRYPOINT ["java", "-jar", "expenseTracker.jar"]
    