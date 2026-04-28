# Finance Manager App

Finance Manager is a smart personal finance management application designed to help users track and control their money efficiently. The app allows users to manage income, expenses, savings, loans, and investments in one secure platform.

It provides a clean user experience, real-time financial insights, and structured reports to support better budgeting and smarter financial decisions.

---

## Features

- User Registration & Login with JWT Authentication
- Secure Role-Based Access Control
- Manage Income Records
- Manage Expense Records
- Track Loans and Repayments
- Manage Investments
- Financial Dashboard & Reports
- REST API Integration
- Clean and Scalable Backend Architecture

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- Maven

### Database
- MySQL / H2 Database

### Tools
- Eclipse / IntelliJ IDEA
- Postman
- Git & GitHub

---

## Project Structure

```text
src/main/java/com/pack/fintech
│── config
│── controller
│── dto
│── entity
│── repository
│── security
│── service
│── util

```


## Security Features

- JWT Token Based Authentication
- Stateless Session Management
- Password Encryption using BCrypt
- Protected APIs
- Role-Based Authorization

---

## API Modules

- Authentication Module
- User Management
- Income Management
- Expense Management
- Investment Management
- Loan Management
- Dashboard / Summary APIs

---

## Getting Started

## Clone Repository

```bash id="l1lp0l"
git clone <your-github-repo-url>

```

## Run Application
```
mvn clean install
mvn spring-boot:run
```

---

## Database Configuration

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_manager
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```


## Future Enhancements

- Mobile App Integration  
- AI Expense Insights  
- Budget Alerts  
- Export Reports (PDF / Excel)  
- Multi Currency Support  
- Savings Goals Tracker  

---

## Author

Developed by Gurpreet Singh

---

## License

This project is open-source and available for learning and development purposes.
