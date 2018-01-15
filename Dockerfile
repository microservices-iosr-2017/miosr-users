FROM maven:3-jdk-8-slim
COPY "target/microservices.users-1.0-SNAPSHOT.jar" "/webapp/"
CMD java -Dspring.config.location=/etc/appconfig/config.properties -jar /webapp/microservices.users-1.0-SNAPSHOT.jar
