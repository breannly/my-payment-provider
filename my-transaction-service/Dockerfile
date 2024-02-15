FROM openjdk:21-slim AS builder
WORKDIR /app
COPY . .

RUN ./gradlew build -x test

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/build/libs/my-transaction-service-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "my-transaction-service-0.0.1-SNAPSHOT.jar"]