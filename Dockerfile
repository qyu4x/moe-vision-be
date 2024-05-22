FROM openjdk:17

MAINTAINER syncrosa.com

WORKDIR /app

EXPOSE 8080

COPY target/live-streaming-service-0.0.1-SNAPSHOT.jar live-streaming-service.jar

ENTRYPOINT ["java", "-jar", "live-streaming-service.jar"]