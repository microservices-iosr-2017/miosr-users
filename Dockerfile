FROM maven:3-jdk-8-slim
COPY "target/microservices.users-1.0-SNAPSHOT.jar" "/webapp/"
EXPOSE 8080
CMD java -jar /webapp/microservices.users-1.0-SNAPSHOT.jar