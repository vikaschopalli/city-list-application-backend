# city-list-application-backend



1. This is spring boot project these service's will be consumed by city-list-application-frontend developed in angular 8.
2. To start this application please clone the repo , import in IDE and run as "Spring Boot Application".
3. This application uses H2 database for the sake of simplicity, once the application start boots up the data from CSV(src/main/resources) will be loaded in **CITY** table in H2. .db
4. Link to H2 Console is http://localhost:8080/h2-console/ , credentials can be referred from corresponding application.properties file.
5. This application uses spring security for restricting API access based on roles. Below users can be used to login via angular frontend application.

| user          | Password              | Role  |
| ------------- |:-------------:| -----:|
| user      | user | ROLE_USER |
| admin      | admin      |   ROLE_ALLOW_EDIT |
