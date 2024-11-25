FROM eclipse-temurin:23.0.1_11-jdk

LABEL maintainer="seahsz"

WORKDIR /app

COPY .mvn .mvn
COPY src src
COPY ./mvnw .
COPY pom.xml .

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day16-0.0.1-SNAPSHOT.jar