# Learning Management System

## Project Description
Learning Management System is an application designed to enhance the learning experience. 
With this platform, students can easily enroll in courses by using
virtual coins. Each course is organized into classroom or video lessons. 
Application supports multitenant approach and course reminders.

## Tech Stack
- **Language**: Java 21
- **Spring Boot**: Spring Framework (Boot, MVC, Data, Security), Hibernate
- **Databases**: H2, PostgreSQL, SAP HANA Database, Liquibase
- **Testing**: JUnit, Mockito, Surefire, Failsafe, JaCoCo
- **VCS**: Git, GitHub
- **Tools**: Docker
- **Cloud**: SAP BTP
- **Additional**: Mustache, Lombok

## Local Setup

### Prerequisites
- **Java 21+**
- **Docker**
- **Maven**

### Steps

1. Clone repository:
```
git clone https://github.com/DailyLemonJam/learning-management-system.git
```
2. Go to folder:
```
cd learning-management-system
```
3. Run docker with PostgreSQL:
```
cd docker
docker compose up -d
```
4. Create secrets file:
```
cd ../src/main/resources
```
Create application-secrets.yaml with following structure:
```
security:
  configuration:
    default-user:
      username: <your_user_username>
      password: <your_user_username>
    manager-user:
      username: <your_manager_username>
      password: <your_manager_password>
```
5. Go back to application and compile it:
```
cd ../../..
mvn clean pacakge
```
6. Run compiled application (use local profile):
```
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```
7. Check if application is working (use previously configured
manager and user properties if needed):
[Health](http://localhost:8080/actuator/health)
[Info](http://localhost:8080/actuator/info)
[Loggers](http://localhost:8080/actuator/loggers)
