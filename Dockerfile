FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/SimCost-0.0.1-SNAPSHOT.jar /app/SimCost-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "SimCost-0.0.1-SNAPSHOT.jar"]