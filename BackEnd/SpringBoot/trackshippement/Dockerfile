FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN mvn dependency:go-offline
COPY ./src ./src
COPY src/main/resources/application.properties.prod src/main/resources/application.properties
RUN mvn clean install

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/trackshippement-0.0.1-SNAPSHOT.jar  app.jar
COPY uploads uploads

EXPOSE 8089
CMD ["java", "-jar", "app.jar"]
