FROM openjdk:16-jdk-alpine
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_O  PTS -jar /app.jar" ]