FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/discovery-0.0.1-SNAPSHOT.jar  app.jar

EXPOSE 8761
CMD ["java", "-jar", "app.jar"]
