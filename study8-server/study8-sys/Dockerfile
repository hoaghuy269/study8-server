FROM openjdk:17-jdk-slim

WORKDIR /app
COPY . .

RUN sed -i 's|${GITHUB_USERNAME}|'${GITHUB_USERNAME}'|g' settings.xml && \
    sed -i 's|${GITHUB_TOKEN}|'${GITHUB_TOKEN}'|g' settings.xml

RUN chmod +x ./mvnw
RUN ./mvnw -s ./settings.xml package -DskipTests

EXPOSE 8200

# Run the application
CMD ["java", "-jar", "target/sys-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=uat"]
