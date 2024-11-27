
# TASK MANAGEMENT SYSTEM

The Task Management System is a web-based application designed to streamline task allocation and management. Built using Spring Boot, Thymeleaf, and MySQL, it allows users to create, assign, track, and update tasks efficiently. This system allows user to view task status, update task status, add comments and upload and download attachments. 


## Tech Stack

**Frontend:**  HTML, CSS, Bootstrap, Javascript


**Backend:** Java, SpringBoot

**Database:** Mysql

**Build tool:** Maven




## Features

- User Registration and Login: User can login using username, role and password

- Task Creation: Create detailed tasks with deadlines.

- Task Reassignment: Reassign tasks among team members.

- Attachment Management: Upload and download attachments (e.g.,.txt, .jpg, .pdf).

- Task Status Updates: Track and update task progress.

- Comments: Add and view comments on tasks.



## Installation

Install Java JDK (version 8 or higher).

Install MySQL.

Install Maven.

Install an IDE like  Eclipse.

1.Clone the Repository:

```bash
git clone https://github.com/yourusername/Website.git
cd Website 

```
2.Configure the Database:

Create a MySQL database named task_management.

Update the application.properties file in src/main/resources with your MySQL credentials:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/task_management
spring.datasource.username=your_username
spring.datasource.password=your_password

```
3.Build the Project:

```bash
mvn clean install

```

4.Run the application

```bash
mvn spring-boot:run

```

5.Access the Application: Open your browser and go to http://localhost:8080.




    
## Usage

This project is used by the following companies:

- Register/Login:
  
  Create an account or log in with existing credentials.

- Manage Tasks:
  
  Create new tasks, assign them to team members, upload     relevant files, and track status.

- Update Tasks:
  
  Reassign tasks or update their status as progress is made.


  https://github.com/user-attachments/assets/a8250383-fc56-473f-89e7-8401fa0f2da0



