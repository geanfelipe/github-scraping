
This project returns the total number of lines and the total number of bytes of all the files of a given public Github repository, grouped by file extension using Web Scraping techniques. 

## stack
1. Spring Boot Web
2. Sprint Boot Cache 
3. Gradle
4. Java 8
5. Docker


## Resource url
[https://blooming-reef-75437.herokuapp.com](https://blooming-reef-75437.herokuapp.com). 

e.g:


```
curl https://blooming-reef-75437.herokuapp.com/info/{owner}/{repository}
```
 
## Swagger 
[https://blooming-reef-75437.herokuapp.com/swagger-ui.html](https://blooming-reef-75437.herokuapp.com/swagger-ui.html)


## Get docker image from docker hub
```
docker pull geanfelipemoura/github-scraping:0.1.0
```

## Execute 
```
$ docker run -p 8080:8080 -e PORT=8080 -t geanfelipemoura/github-scraping:0.1.0
```


after that , you will be able to test locally

e.g

get informations from user geanfelipe's hello-microservice-message repository
```
curl http://localhost:8080/info/geanfelipe/hello-microservice-message
```
