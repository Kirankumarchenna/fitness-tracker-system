# README for Fitness Tracker & Coaching System

## Summary
This project for the fitness tracker and coaching system, where it helps people to manage their workouts and track their fitness progress.
It allows trainers to create workout plans and users to follow while recording their daily work activities.

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**
- **JWT (jwt)**
- **Spring Data JPA + Hibernate**
- **H2 Database** (development) / **MySQL** (production)
- **Lombok**
- **Swagger/OpenAPI**
- **Maven**

## Project Structure

```
fitness-tracker-system/
 src/main/java/com/fitnesstracker/
    |-- controller/          # REST Controllers
    |-- service/             # Business Logic
    |-- repository/          # Data Access
    |-- entity/              # JPA Entities
    |-- dto/                 # Data Transfer Objects
    |-- security/            # JWT & Security
    |-- exception/           # Custom Exceptions
    |-- config/              # Configuration Classes
    |-- FitnessTrackerApplication.java
src/main/resources/
    |-- application.yml      # Main Configuration
pom.xml
```

## User Roles

- **USER**: Regular fitness user who can log workouts and track progress
- **TRAINER**: Can create and manage workout plans
- **ADMIN**: System administrator with full access

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login and get JWT token

### Users
- `GET /api/v1/users/me` - Get current user profile
- `GET /api/v1/users/{userId}` - Get user profile
- `GET /api/v1/users/trainers/all` - Get all trainers

### Workout Plans
- `POST /api/v1/workout-plans` - Create workout plan (TRAINER)
- `GET /api/v1/workout-plans` - Get all active plans
- `GET /api/v1/workout-plans/{planId}` - Get plan details
- `GET /api/v1/workout-plans/trainer/{trainerId}` - Get trainer's plans
- `PUT /api/v1/workout-plans/{planId}` - Update plan (TRAINER)
- `DELETE /api/v1/workout-plans/{planId}` - Delete plan (TRAINER)

### Workout Logs
- `POST /api/v1/workout-logs` - Create workout log
- `GET /api/v1/workout-logs/{logId}` - Get log details
- `GET /api/v1/workout-logs/my-history` - Get current user's history
- `GET /api/v1/workout-logs/user/{userId}` - Get user's history
- `PUT /api/v1/workout-logs/{logId}` - Update log
- `DELETE /api/v1/workout-logs/{logId}` - Delete log

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Git

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Kirankumarchenna/fitness-tracker-system
cd fitness-tracker-system
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### Access H2 Console (Development Only)
```
http://localhost:8080/h2-console
```

## Sample Credentials

### Admin
- Email: `admin@fitnesstracker.com`
- Password: `admin123`

### Trainer
- Email: `kiran@trainer.com`
- Password: `kiran123`

### User
- Email: `john@user.com`
- Password: `john123`


## Testing with Postman

1. Import `postman_collection.json` into Postman
2. Set the `jwt_token` variable after login
3. Use pre-configured requests to test APIs


##  License

This project is licensed under the Apache License 2.0.

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the ## fitness-tracker-system