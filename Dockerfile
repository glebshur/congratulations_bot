FROM openjdk:11
WORKDIR /app
COPY target/congratulations_bot-2.0.0-SNAPSHOT.jar ./congratulations_bot-2.0.0.jar
ENTRYPOINT [ "java", "-jar", "./congratulations_bot-2.0.0.jar" ]