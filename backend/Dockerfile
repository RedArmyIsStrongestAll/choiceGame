FROM openjdk:22-jdk-slim as build

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests


FROM openjdk:22-jdk-slim

WORKDIR /app

COPY --from=build /app/target/choiceGame-0.0.1.jar /app/choiceGame.jar

EXPOSE 7070

ENTRYPOINT ["java", "-jar", "/app/choiceGame.jar"]
