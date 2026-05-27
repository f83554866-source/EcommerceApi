# ShopBasic Ecommerce API

## 📌 Project Overview
This is a RESTful API built using Spring Boot that manages an e-commerce product catalog. It supports full CRUD operations, filtering, and cross-origin resource sharing (CORS) to serve a dynamic vanilla JavaScript frontend. Data is persisted using a relational database via Spring Data JPA.

---

## 🚀 How to Run the Project

1. Open terminal in the project folder
2. Run the Spring Boot server:
   ```bash
   ./gradlew bootRun
The server runs at: http://localhost:8080

To view the frontend, open the frontend directory in VS Code and start Live Server on port 5500.

📦 Tech Stack
Backend: Spring Boot, Java, Gradle

Database: Spring Data JPA, Hibernate, Relational Database

Frontend Integration: Vanilla JavaScript (Fetch API), HTML/CSS, CORS Configuration

🗄️ Database Schema
The relational database consists of the following primary tables and relationships:

Category: Stores id and name. One Category can have many Products (One-to-Many).

Product: Stores id, name, description, price, and imageUrl. It holds a foreign key (category_id) linking it to the Category table (Many-to-One).

OrderEntity: Stores the main order record details.

OrderItem: Maps specific products to a specific Order (Many-to-One).

🔗 API Endpoints
GET all products
GET /api/products

GET product by ID
GET /api/products/{id}

CREATE product
POST /api/products

UPDATE product (PUT)
PUT /api/products/{id}

PARTIAL UPDATE (PATCH)
PATCH /api/products/{id}

DELETE product
DELETE /api/products/{id}

FILTER products
GET /api/products/filter?filterType=category&filterValue=Electronics

📸 Proof of Integration
1. Database Populated with Data
![ConsoleSucces](images/console-success.jpg)

2. Successful Frontend Fetch
![frontend](images/landingpage.jpg)