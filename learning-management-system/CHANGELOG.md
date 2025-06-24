# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.3.0] - 2025-06-01 - Author: Mikita Darafeichuk

### Added

- Language field to Student
- JPA Inheritance (ClassroomLesson and VideoLesson)
- Audit fields
- Pagination for GET methods
- Surefire, Failsafe, JaCoCo
- Integration tests for CourseController and LessonController

## [1.2.0] - 2025-05-21 - Author: Mikita Darafeichuk

### Added

- Spring Actuator
- In-memory users for Actuator basic auth
- PostgreSQL in Docker
- mta.yaml

### Removed

- H2 in-memory database

### Fixed

- Potential "Lost update" issue
- Refresh token logic

## [1.1.0] - 2025-05-10 - Author: Mikita Darafeichuk

### Added

- SAP BTP Cloud setup
- Destination, Feature flag and User-provided services
- Enable remote debug
- Common OAuth2TokenClient

### Changed

- Project structure from by-layer to by-feature

## [1.0.0] - 2025-04-25 - Author: Mikita Darafeichuk

### Added

- Project backbone
- Update dependencies
- CRUD for Courses, Lessons and Students
- Implement Liquibase migrations
- Global Exception Handler
- Spring Boot Actuator health, info and loggers endpoints
- Swagger
- Validation for DTOs
- Disable open-in-view
- Job for sending course reminder emails
- Payments (coins transaction) logic
- Unit and Integration Tests
