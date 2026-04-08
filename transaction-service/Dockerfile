# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copia apenas o POM primeiro
COPY pom.xml .

# 2. Baixa as dependências (esta camada será cacheada pelo Docker)
# Se a internet cair, o Docker tentará retomar daqui
RUN mvn dependency:go-offline -B

# 3. Agora copia o código fonte e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio de Runtime (igual ao anterior)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]