# Manulife-FE with Vaadin

## Overview
This project is a frontend application built using **Vaadin** for managing `User` entities with **CRUD operations** and **report generation**.

## Features
- CRUD operations for the `User` entity
- Reporting feature to generate a report on user data

## Tech Stack
- **Vaadin** (Frontend framework)
- **Spring Boot** (Backend integration)
- **JasperReports** (For reporting)
- **MySQL** (Configurable database)
- **Maven** (Dependency management)

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/manulife-fe.git
cd manulife-fe
```

### 2. Build and Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints
| Method     | Endpoint        | Description                |
| ---------- | --------------- | -------------------------- |
| **POST**   | `/users`        | Create a new user          |
| **GET**    | `/users`        | Get all users              |
| **GET**    | `/users/{id}`   | Get user by ID             |
| **PUT**    | `/users/{id}`   | Update user by ID          |
| **DELETE** | `/users/{id}`   | Delete user by ID          |
| **GET**    | `/users/report` | Generate user report (PDF) |
