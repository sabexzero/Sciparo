FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/Sciparo-0.0.2-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
