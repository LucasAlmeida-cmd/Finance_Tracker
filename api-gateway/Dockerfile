# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copia o POM e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copia o código e gera o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio de Runtime (Javá 21)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]