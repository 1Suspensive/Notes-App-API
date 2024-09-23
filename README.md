# NOTES API

This project was generated with [SpringBoot] version 3.2.2 and [JAVA] version 17.

## Development server

Run `./mvnw spring-boot:run` for a dev server. Navigate to `http://localhost:8080/swagger-ui/index.html` to see the documentation.

## Deploy on Docker

Open the root project folder, then run `docker-compose up`. Navigate to `http://localhost:8080/swagger-ui/index.html` to see the documentation. If any change is done, you must
run `docker-compose down` and remove the notes-api-image created, then re-run `docker-compose up` in order to see the changes.

To get more help on the SpringBoot use go check out the [SpringBoot Getting Started Guide](https://github.com/spring-guides/gs-spring-boot.git) page.
