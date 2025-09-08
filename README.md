Ahad Job Portal

A microservices-based Job Portal application built with Spring Boot, featuring User, Company, Job, and Notification services, integrated with Kafka for event-driven communication and JWT Authentication for secure access.

🚀 Features

User Service – Handles user registration, authentication, and profiles.

Company Service – Manages company details and job postings.

Job Service – Supports job creation, listing, applications, and management.

Notification Service – Sends real-time notifications using Kafka.

JWT Authentication – Secure login and token-based authorization across services.

Microservices Architecture – Each service is independent, scalable, and communicates via REST and Kafka.

🛠️ Tech Stack

Backend: Java, Spring Boot, Spring Security, Spring Data JPA

Database: MySQL (can be extended to MongoDB if required)

Messaging: Apache Kafka

Authentication: JWT

Service Communication: REST API + Kafka

Build Tool: Maven

Deployment Ready For: Docker, Kubernetes, Cloud

📌 Services Overview

User Service

User registration & login (JWT)

User profile management

Company Service

Add & manage companies

Post new jobs

Job Service

Job creation, update, delete

Job applications

Notification Service

Kafka-based event notifications (e.g., job applied, job posted)

🔐 Authentication Flow

User logs in → JWT generated → Token included in every request

API Gateway / Services validate token before granting access
