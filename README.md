# city-list-application-backend
1. This is spring boot project these service's will be consumed by city-list-application-frontend developed in angular.
2. To start this application please clone this repo, import in any IDE and run as "Spring Boot Application".
3. This application uses H2 database, once the application start the data from CSV(src/main/resources) will be automatically loaded in **CITY** table in H2 db.
4. As part of initial loading **USER** table will also be created and below mentioned user will be automatically added to the table for authentication purpose
5. Link to H2 Console is http://localhost:8080/h2-console/ , credentials can be referred from corresponding application.properties file.
6. This application uses spring security for restricting API access based on roles. Below users can be used to login via angular frontend application.

Note:
Password are kept as plain text for demo purpose ,we should never use plain text password in production environment :)

| user          | Password              | Role  |
| ------------- |:-------------:| -----:|
| user      | user | ROLE_USER |
| admin      | admin      |   ROLE_ALLOW_EDIT |
