
### Usage
```
$ cd spring-boot
$ docker build -t spring-boot-image .
$ docker run --name spring-boot-container spring-boot-image

$ cd nginx
$ docker build -t nginx-image .
$ docker run --name nginx-container -p 8080:80 --link spring-boot-container nginx-image
```
