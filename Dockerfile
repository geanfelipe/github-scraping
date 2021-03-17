FROM openjdk:8-jdk-alpine

VOLUME /app
COPY build/libs/github-scraping*.jar /app/github-scraping.jar
WORKDIR /app

CMD java -jar github-scraping.jar