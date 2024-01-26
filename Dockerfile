FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

COPY . .

RUN ./gradlew assemble

FROM openjdk:17-oracle

WORKDIR /app

COPY --from=builder /app/build/libs/postech-lanchonete-producao-query-1.0.0-POC.jar .

EXPOSE 8080

CMD ["java", "-jar", "lanchonetebairro-1.0.0-POC.jar"]