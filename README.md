# 🧑‍💼 Ahad Job Portal

A **microservices-based Job Portal Application** built with **Spring Boot**, providing a **secure**, **scalable**, and **event-driven architecture**.
It connects **Users** and **Companies**, enabling job creation, applications, and real-time notifications — all managed through **Spring Cloud**, **Kafka**, and **JWT Authentication**.

---

## 🚀 Overview

**Ahad Job Portal** is designed using a **microservices architecture**, where each service (User, Company, Job, Notification, Auth, etc.) operates independently and communicates via **Apache Kafka**.
It ensures secure access using **JWT Authentication**, centralized configuration through **Spring Cloud Config**, and intelligent routing using **Spring Cloud Gateway**.

---

## 🧩 Microservices Architecture

### 1. **User-Service**

* Handles user registration, updates, and profile management.
* Manages job applications submitted by users.
* Exposes REST APIs for retrieving and updating user details.

### 2. **Company-Service**

* Manages company registration and profile updates.
* Enables companies to post jobs and view applicants.

### 3. **Job-Service**

* Handles job creation, updates, and applications.
* Connects companies and users through job postings.
* Publishes Kafka events for new jobs and status changes.

### 4. **Notification-Service**

* Listens to Kafka topics for job events and application updates.
* Sends notifications when:

  * A new job is posted.
  * A user’s application status changes.

### 5. **Auth-Service**

* Manages login and JWT token generation.
* Validates tokens for secured endpoints across all services.
* Supports role-based access (`USER`, `COMPANY`).

### 6. **API Gateway**

* Routes incoming requests to appropriate microservices.
* Performs **JWT validation** for protected routes.
* Single entry point for all external API requests.

### 7. **Eureka Server**

* Handles service discovery and registration dynamically.

### 8. **Config Server**

* Manages centralized configurations using **Spring Cloud Config**.
* Loads properties from GitHub Config Repository:
  🔗 [Ahad Job Configs](https://github.com/harunsheikh42786/Ahad-Job-Configs.git)

---

## ⚙️ Tech Stack

| Layer                 | Technology                |
| --------------------- | ------------------------- |
| **Backend Framework** | Spring Boot, Spring Cloud |
| **Service Discovery** | Eureka Server             |
| **API Gateway**       | Spring Cloud Gateway      |
| **Security**          | Spring Security, JWT      |
| **Messaging**         | Apache Kafka              |
| **Configuration**     | Spring Cloud Config       |
| **Database**          | MySQL                     |
| **Build Tool**        | Maven                     |
| **Version Control**   | Git & GitHub              |

---

## 🌐 API Gateway Routing Configuration

The **API Gateway** (running on port `8080`) handles all routing and security.
Routes are categorized as **Public** (no JWT required) or **Protected** (JWT required).

| Access Type      | Service                  | Routes                                                                                                                   | Description                                                       |
| ---------------- | ------------------------ | ------------------------------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------- |
| 🟢 **Public**    | **User-Service**         | `/api/v1/register/user`                                                                                                  | Register a new user                                               |
| 🟢 **Public**    | **Company-Service**      | `/api/v1/register/company`                                                                                               | Register a new company                                            |
| 🟢 **Public**    | **Auth-Service**         | `/api/v1/auth/**`, `/authenticate`                                                                                       | Login and generate JWT                                            |
| 🔒 **Protected** | **User-Service**         | `/api/v1/users/**`, `/api/v1/user-info/**`, `/api/v1/educations/**`, `/api/v1/achievements/**`, `/api/v1/job-history/**` | Manage user details and applications                              |
| 🔒 **Protected** | **Company-Service**      | `/api/v1/companies/**`, `/api/v1/company-information/**`, `/api/v1/company-addresses/**`                                 | Manage company profile and details                                |
| 🟡 **Mixed**     | **Job-Service**          | `/api/v1/jobs/**`, `/api/v1/applications/**`, `/api/v1/categories/**`                                                    | Public GET for listings, Protected POST/PUT/DELETE for management |
| 🔒 **Protected** | **Notification-Service** | `/api/v1/notifications/**`, `/api/v1/emails/**`, `/api/v1/alerts/**`                                                     | Manage notifications and alerts                                   |

---

### 🔐 JWT Configuration

```yaml
jwt:
  secret: "mysecretkey123456789012345678901234"
```

**Header Example:**

```http
Authorization: Bearer <your_jwt_token>
```

---

### 🩺 Management Endpoints

| Endpoint            | Description              |
| ------------------- | ------------------------ |
| `/actuator/health`  | Check application health |
| `/actuator/info`    | Display app information  |
| `/actuator/metrics` | View runtime performance |

---

### 🧾 Logging Configuration

```yaml
logging:
  level:
    org.springframework.cloud.gateway: DEBUG
    com.ahad.gateway.filter: DEBUG
```

---

## 🔐 Authentication Flow

1. **User/Company registers** through their respective endpoints.
2. **Auth-Service** verifies login credentials.
3. On success, a **JWT Token** is generated and sent back.
4. Every request passes through **API Gateway**, where the token is validated.
5. Access is granted or denied based on user role (`USER`, `COMPANY`).

---

## 🔄 Event-Driven Communication (Kafka)

* **Producer:** Job-Service (publishes job and application events).
* **Consumer:** Notification-Service (listens and sends user notifications).
* Enables **asynchronous and real-time** communication between services.

---

## 🧠 Key Features

✅ Microservices Architecture
✅ Secure JWT Authentication
✅ Kafka-based Event Handling
✅ Centralized Configuration via Spring Cloud Config
✅ Service Discovery with Eureka
✅ API Gateway Routing & Filtering
✅ Role-Based Access Control (User/Company)
✅ Real-time Notifications

---

## 📁 Project Structure

```
Ahad-Job-Portal/
│
├── api-gateway/
├── auth-service/
├── user-service/
├── company-service/
├── job-service/
├── notification-service/
├── eureka-server/
├── config-server/
└── README.md
```

---

## 🧑‍💻 Setup Instructions

1. **Clone the project**

   ```bash
   git clone https://github.com/harunsheikh42786/Ahad-Job-Portal.git
   cd Ahad-Job-Portal
   ```

2. **Run Config Server**

   ```yaml
   spring.cloud.config.server.git.uri=https://github.com/harunsheikh42786/Ahad-Job-Configs.git
   ```

3. **Start Eureka Server**

   * Run Eureka to register microservices dynamically.

4. **Start Microservices**

   * Run: `User-Service`, `Company-Service`, `Job-Service`, `Notification-Service`, `Auth-Service`, and `API Gateway`.

5. **Kafka Setup**

   * Install and run Kafka locally or via Docker.
   * Update Kafka config in each service’s `application.yml`.

6. **Access Points**

   * API Gateway: `http://localhost:8080`
   * Eureka Dashboard: `http://localhost:8761`

---

## 🧪 Example Endpoints

| Service              | Endpoint                   | Description            |
| -------------------- | -------------------------- | ---------------------- |
| User-Service         | `/api/v1/register/user`    | Register a new user    |
| Company-Service      | `/api/v1/register/company` | Register a new company |
| Auth-Service         | `/api/v1/auth/login`       | Login and generate JWT |
| Job-Service          | `/api/v1/jobs/`            | Manage job listings    |
| Notification-Service | `/api/v1/notifications/`   | Fetch notifications    |

---

## 🌍 Repository Links

* **Main Project:** [Ahad-Job-Portal (Backend)](https://github.com/harunsheikh42786/Ahad-Job-Portal.git)
* **Config Repository:** [Ahad-Job-Configs](https://github.com/harunsheikh42786/Ahad-Job-Configs.git)

---

## 🧑‍🏫 Author

**👤 Muhammed Harun Sheikh**
🎓 Java Developer
💼 Specialized in Spring Boot, React, Microservices, and Cloud Deployment
📫 [GitHub Profile](https://github.com/harunsheikh42786)
📫 [LinkedIn Profile](https://www.linkedin.com/in/harun-sheikh-83974727a/)

---

## 🏗️ Future Enhancements

* 🧠 AI-powered job recommendations
* 📬 Email notifications integration
* ☁️ Deployment on AWS / Docker & Kubernetes
* 📱 Frontend with React + Tailwind UI

---

Would you like me to also create a **Mermaid architecture diagram** (showing all microservices + Gateway + Kafka + Database flow) to add under the "Architecture" section of this README? It’ll make it visually professional for GitHub.
