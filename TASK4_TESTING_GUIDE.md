# Task 4: REST Controller Testing Guide - Postman

## Application Status
✅ Application started successfully on `http://localhost:8080`
✅ Database connected (MySQL)
✅ All endpoints operational with database persistence

---

## Test Scenarios

### 1. **Create a Product (Test Database Persistence)**

**Request:**
```
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High-performance gaming laptop",
  "price": 45000.0,
  "imageUrl": "laptop.jpg",
  "category": {
    "id": 1,
    "name": "Electronics"
  }
}
```

**Expected Response:** 
- Status: `201 Created`
- Response Body:
```json
{
  "id": 3,
  "name": "Laptop",
  "description": "High-performance gaming laptop",
  "price": 45000.0,
  "imageUrl": "laptop.jpg",
  "category": {
    "id": 1,
    "name": "Electronics"
  }
}
```

**Verification:** Save the product ID returned in the response for further tests.

---

### 2. **Get All Products (Verify Persistence After Creation)**

**Request:**
```
GET http://localhost:8080/api/v1/products
```

**Expected Response:**
- Status: `200 OK`
- Response includes newly created product and initial sample data:
```json
[
  {
    "id": 1,
    "name": "Mouse",
    "description": "Gaming Mouse",
    "price": 500.0,
    "imageUrl": "mouse.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  },
  {
    "id": 2,
    "name": "Keyboard",
    "description": "Mechanical Keyboard",
    "price": 1500.0,
    "imageUrl": "keyboard.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  },
  {
    "id": 3,
    "name": "Laptop",
    "description": "High-performance gaming laptop",
    "price": 45000.0,
    "imageUrl": "laptop.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  }
]
```

---

### 3. **Get Product by ID**

**Request:**
```
GET http://localhost:8080/api/v1/products/3
```

**Expected Response:**
- Status: `200 OK`
- Returns the specific product (Laptop with ID 3)

---

### 4. **Get Non-Existent Product (Error Handling - 404)**

**Request:**
```
GET http://localhost:8080/api/v1/products/999
```

**Expected Response:**
- Status: `404 Not Found`
- Response Body:
```json
{
  "timestamp": "2026-05-27T15:55:00.123+08:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found",
  "path": "/api/v1/products/999"
}
```

---

### 5. **Filter by Category (Database Query)**

**Request:**
```
GET http://localhost:8080/api/v1/products/filter?filterType=category&filterValue=Electronics
```

**Expected Response:**
- Status: `200 OK`
- Returns all products in "Electronics" category from database:
```json
[
  {
    "id": 1,
    "name": "Mouse",
    "description": "Gaming Mouse",
    "price": 500.0,
    "imageUrl": "mouse.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  },
  {
    "id": 2,
    "name": "Keyboard",
    "description": "Mechanical Keyboard",
    "price": 1500.0,
    "imageUrl": "keyboard.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  },
  {
    "id": 3,
    "name": "Laptop",
    "description": "High-performance gaming laptop",
    "price": 45000.0,
    "imageUrl": "laptop.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  }
]
```

**Verification:** All results should come from database query, not in-memory filtering.

---

### 6. **Filter by Name (Database Query)**

**Request:**
```
GET http://localhost:8080/api/v1/products/filter?filterType=name&filterValue=Mouse
```

**Expected Response:**
- Status: `200 OK`
- Returns product(s) with matching name:
```json
[
  {
    "id": 1,
    "name": "Mouse",
    "description": "Gaming Mouse",
    "price": 500.0,
    "imageUrl": "mouse.jpg",
    "category": {
      "id": 1,
      "name": "Electronics"
    }
  }
]
```

---

### 7. **Invalid Filter Parameters (Error Handling - 400)**

**Request:**
```
GET http://localhost:8080/api/v1/products/filter?filterType=&filterValue=Electronics
```

**Expected Response:**
- Status: `400 Bad Request`
- Response Body:
```json
{
  "timestamp": "2026-05-27T15:55:30.456+08:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid argument: filterType parameter is required",
  "path": "/api/v1/products/filter"
}
```

---

### 8. **Update Product (PUT)**

**Request:**
```
PUT http://localhost:8080/api/v1/products/3
Content-Type: application/json

{
  "name": "Gaming Laptop Pro",
  "description": "Ultra-high-performance gaming laptop",
  "price": 65000.0,
  "imageUrl": "laptop-pro.jpg",
  "category": {
    "id": 1,
    "name": "Electronics"
  }
}
```

**Expected Response:**
- Status: `200 OK`
- Returns updated product with all new values persisted to database

---

### 9. **Partial Update Product (PATCH)**

**Request:**
```
PATCH http://localhost:8080/api/v1/products/3
Content-Type: application/json

{
  "price": 55000.0
}
```

**Expected Response:**
- Status: `200 OK`
- Returns product with only price updated, other fields unchanged

---

### 10. **Invalid Create (Error Handling - 400)**

**Request:**
```
POST http://localhost:8080/api/v1/products
Content-Type: application/json

{
  "name": "",
  "description": "Invalid product",
  "price": -100.0,
  "imageUrl": "invalid.jpg"
}
```

**Expected Response:**
- Status: `400 Bad Request`
- Response Body:
```json
{
  "timestamp": "2026-05-27T15:56:00.789+08:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid argument: Product name is required",
  "path": "/api/v1/products"
}
```

---

### 11. **Delete Product**

**Request:**
```
DELETE http://localhost:8080/api/v1/products/3
```

**Expected Response:**
- Status: `204 No Content`
- No response body
- Product removed from database

---

### 12. **Verify Data Persistence (Server Restart Test)**

**Steps:**
1. Create a product (see Test 1)
2. Get all products to confirm creation (see Test 2)
3. Stop the server (Ctrl+C in terminal)
4. Restart the server: `.\gradlew.bat bootRun`
5. Make GET request to retrieve all products

**Expected Result:**
- ✅ Newly created product is still in database
- ✅ Data persists after server restart (unlike Lab 7)
- ✅ Sample data (Mouse, Keyboard) is also still present

**Request after restart:**
```
GET http://localhost:8080/api/v1/products
```

**Expected:** All previously created products are still there.

---

## Summary of Tests

| Test # | Name | Method | Endpoint | Expected Status | Database Test |
|--------|------|--------|----------|-----------------|---------------|
| 1 | Create Product | POST | /api/v1/products | 201 | ✅ CREATE |
| 2 | Get All Products | GET | /api/v1/products | 200 | ✅ READ |
| 3 | Get by ID | GET | /api/v1/products/3 | 200 | ✅ READ |
| 4 | Not Found Error | GET | /api/v1/products/999 | 404 | Error Handling |
| 5 | Filter by Category | GET | /api/v1/products/filter | 200 | ✅ Query |
| 6 | Filter by Name | GET | /api/v1/products/filter | 200 | ✅ Query |
| 7 | Invalid Filter | GET | /api/v1/products/filter | 400 | Validation |
| 8 | Update Product | PUT | /api/v1/products/3 | 200 | ✅ UPDATE |
| 9 | Patch Product | PATCH | /api/v1/products/3 | 200 | ✅ UPDATE |
| 10 | Invalid Create | POST | /api/v1/products | 400 | Validation |
| 11 | Delete Product | DELETE | /api/v1/products/3 | 204 | ✅ DELETE |
| 12 | Restart Test | GET | /api/v1/products | 200 | ✅ PERSISTENCE |

---

## Error Handling Verification

### Exceptions Handled:

1. **RuntimeException (404 Not Found)**
   - Thrown when product not found
   - Caught by GlobalExceptionHandler
   - Returns JSON with timestamp, status, error, message, path

2. **DataIntegrityViolationException (400 Bad Request)**
   - Thrown on constraint violations
   - Caught by GlobalExceptionHandler
   - Returns detailed error information

3. **IllegalArgumentException (400 Bad Request)**
   - Thrown for invalid arguments (empty name, negative price, etc.)
   - Caught by GlobalExceptionHandler
   - Clear error messages for debugging

4. **General Exception (500 Internal Server Error)**
   - Fallback for unexpected errors
   - Caught by GlobalExceptionHandler
   - Generic error response

---

## Key Improvements vs Lab 7

| Feature | Lab 7 | Task 4 |
|---------|-------|--------|
| Data Storage | ArrayList (in-memory) | MySQL Database (persistent) |
| Data Persistence | ❌ Lost on restart | ✅ Persists on restart |
| Error Handling | Basic | ✅ Comprehensive GlobalExceptionHandler |
| Query Filtering | In-memory streams | ✅ Database queries via repositories |
| Input Validation | Minimal | ✅ Detailed validation |
| API Documentation | Limited | ✅ JSDoc comments on all endpoints |
| HTTP Status Codes | Basic | ✅ Proper status codes (201, 204, etc.) |
| JSON Error Responses | Simple | ✅ Detailed with timestamp, path |

