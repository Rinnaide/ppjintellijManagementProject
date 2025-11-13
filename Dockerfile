FROM maven:3.9.9-amazoncorretto-21-alpine AS build
COPY . .
RUN mvnw clean package -DskipTests
RUN ls -la /target/projectmanagement-0.0.1-SNAPSHOT.jar
FROM amazoncorretto:21-alpine
COPY --from=build target/projectmanagement-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
CMD ["java", "-jar", "/app.jar"]