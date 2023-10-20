FROM maven:3.9.4-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.17-jdk
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ENTRYPOINT java -jar app.jar