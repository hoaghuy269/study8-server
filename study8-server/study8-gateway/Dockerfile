FROM openjdk:17-jdk-slim

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests
EXPOSE 8300

# Run the application
CMD ["java", "-jar", "target/gateway-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=uat"]