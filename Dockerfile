# syntax = docker/dockerfile:1.0-experimental

# Build Source Code
FROM maven:3.6.3-jdk-11-slim as BUILD

WORKDIR /build
COPY pom.xml .
COPY src/ /build/src/

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package

# Create Final Image
FROM amazoncorretto:11-al2-jdk as final

COPY --from=BUILD /build/target/*.jar /

ENTRYPOINT ["java", "-jar"]
CMD [""]
