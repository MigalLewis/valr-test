# Getting Started

To run the application you would first need to compile the code and then you can access the jar from the target folder

`mvn clean install`

`java -jar target/valr-test-0.0.1-SNAPSHOT.jar`

by default the application runs on port http://localhost:8080

### Swagger
you can find the swagger working documentation at http://localhost:8080/swagger-ui.html

### H2
you can access h2 database http://localhost:8080/h2-console

url = jdbc:h2:mem:testdb
username = sa
password =

### Health Check
you can see if the service is up and running by calling http://localhost:8080/actuator/health

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

