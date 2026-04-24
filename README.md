# Ecommerce API

## 📌 Project Overview
This is a simple RESTful API built using Spring Boot that manages an in-memory product catalog. It supports full CRUD operations and filtering.

---

## 🚀 How to Run the Project

1. Open terminal in project folder
2. Run:

./gradlew bootRun


3. Server runs at:

http://localhost:8080


---

## 📦 Tech Stack
- Spring Boot
- Java
- Gradle
- In-memory List storage

---

## 🔗 API Endpoints

### GET all products

GET /api/v1/products


### GET product by ID

GET /api/v1/products/{id}


### CREATE product

POST /api/v1/products


### UPDATE product (PUT)

PUT /api/v1/products/{id}


### PARTIAL UPDATE (PATCH)

PATCH /api/v1/products/{id}


### DELETE product

DELETE /api/v1/products/{id}


### FILTER products

GET /api/v1/products/filter?filterType=category&filterValue=Category 1


---

## ⚠️ Notes
- Uses in-memory storage (List<Product>)
- Data resets when server restarts

---

## 📸 Testing Proof
All endpoints tested using Thunder Client / Postman.