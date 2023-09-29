**Stack:**
- Java `17`
- Maven `3.9.4`
- Spring Boot `2.6.15`
- Swagger2 `3.0.0`
- Lombok
- Apache Derby `10.14.2`
- JUnit `5.10.0`

### Running all Services Local

**Required Local Installed**

- Java 17
- Maven

### Will build the project and run the integration test ###

```
mvn clean install
```

### To run the project without any IDE ###

```
java -jar movies-0.0.1-SNAPSHOT.jar
```
**for running with java comand line, the 'movielist-csv' file must be at same folder as de .JAR file generated** \
**NOTE** : this project already includes a 'movielist.csv', but it can be changed and should run correctly with any other file with same name and collumn disposition.

This project already include the integration test .CSV file especific for its case in the root folder, any other file will fail the test.

Project will run on port http://localhost:8080 \
This project has a swagger page, found at http://localhost:8080/swagger-ui/