FROM openjdk:8-jdk-alpine

VOLUME /app
COPY build/libs/github-scraping*.jar /tmp/github-scraping.jar
WORKDIR /app

ENTRYPOINT java -jar github-scraping.jar

EXPOSE 8080