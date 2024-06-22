FROM openjdk:17-jdk-slim

WORKDIR /app
COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw package ./settings.xml -DskipTests

EXPOSE 8200

# Run the application
CMD ["java", "-jar", "target/sys-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=uat"]
