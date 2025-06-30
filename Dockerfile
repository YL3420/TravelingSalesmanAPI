FROM openjdk:17-jdk-slim
LABEL maintainer="yl3420"
COPY target/tsp_approx-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]