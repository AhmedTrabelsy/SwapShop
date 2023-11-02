FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
COPY src/main/resources/application.properties.prod src/main/resources/application.properties
RUN mvn clean package

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/category-0.0.1-SNAPSHOT.jar  app.jar

EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
