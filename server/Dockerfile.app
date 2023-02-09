FROM registry.openanalytics.eu/library/openjdk:16-jdk-slim-buster
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
