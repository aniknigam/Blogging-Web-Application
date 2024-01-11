
# Blogging Web Application - Backend

This is the backend component of a modern blogging web application built using Spring Boot. The application supports CRUD operations for entities like User, Post, Post Category, and Comment. It includes features such as user authentication using JWT tokens, password encoding, one-to-many and many-to-one relationships, exception handling, and integration with a MySQL database. The project also utilizes the ModelMapper library for entity mapping.



## Features

- **JWT Token:**
  - Sign up and login functionality with JWT token authentication.
  - Passwords are securely stored in encoded form.

- **Entity Relationships:**
  - Utilizes one-to-many and many-to-one relationships for managing posts, comments, and categories.

- **Rest Api:**
  - Implemented a rest api for (GET, POST, PUT, DELETE, PATCH)

- **Exception Handling:**
  - Global exception handling to ensure robust error management.

- **Database Integration:**
  - Interacts with a MySQL database to store and retrieve data.

- **ModelMapper:**
  - Utilizes ModelMapper for efficient mapping between DTOs and entities.



##  Prerequisites

- Java JDK 
- Maven 
- MySQL 
## Database Structure 

![App Screenshot](https://drive.google.com/file/d/1D92q0B3A_BdEjKFCxoH4a82ch6XqSzSx/view?usp=drive_link)


## Clone the repository:

Command -> git clone https://github.com/mhamp/bloggingapp.git