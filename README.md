# Match Service

This is a Spring Boot application that provides a RESTful API for managing cricket matches.

## Prerequisites

- Java 11 or 17
- Maven

## Running the Application

1. Clone the repository:
    ```sh
    git clone https://github.com/Vinayakvmahajan/score-service.git
    cd match-service
    ```

2. Build and run the application:
    ```sh
    mvn spring-boot:run
    ```

3. Access the H2 console:
    - URL: `http://localhost:8080/h2-console`
    - JDBC URL: `jdbc:h2:mem:testdb`
    - Username: `sa`
    - Password: `password`

4. Access the Swagger UI:
    - URL: `http://localhost:8080/swagger-ui.html`

## API Endpoints

- `POST /scores`: Add a new score
- `GET /scores/{id}`: Get a score by Id
- `GET /scores/match/{matchId}`: Get a score by match Id
- `PUT /scores/{id}`: Update a score
- `DELETE /scores/{id}`: Delete a score

## Running Tests

To run tests, use the following command:
```sh
mvn test
