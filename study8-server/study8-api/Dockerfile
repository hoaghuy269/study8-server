FROM openjdk:17-jdk-slim

WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw package -DskipTests
EXPOSE 8761

# Run the application
CMD ["java", "-jar", "target/api-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=uat"]