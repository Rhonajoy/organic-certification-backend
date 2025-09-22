#  Organic Certification Backend (Spring Boot)

This is the backend service for the **Organic Certification Platform**, built with **Spring Boot (Java 21)**.  
It provides REST APIs for managing farmers, farms, fields, and inspection workflows.

---

##  Tech Stack
- **Java 21**
- **Spring Boot 3**
- **PostgreSQL** (Cloud-hosted in production, Docker in local development)
- **Maven** (build tool)
- **Docker & Docker Compose** (containerization & orchestration)
- **Railway** (deployment platform)

---

##  Features
- Farmer, Farm, and Field registration
- Inspections & compliance certification
- Cloud-hosted PostgreSQL integration
- Docker support for easy setup

---

## 🛠 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/Rhonajoy/organic-certification-backend.git
cd organic-certification-backend

### 2. Build the jar
```bash
./mvnw clean package -DskipTests
```
##  Run with Docker

```bash
    docker compose up --build
```

##  API Documentation
http://localhost:8080/swagger-ui.html

https://organic-certification-backend-production.up.railway.app/swagger-ui/index.html

## License

This project is licensed under the MIT License – free to use and modify.


