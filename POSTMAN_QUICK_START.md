# Postman Quick Start - Task 4 Testing

## Setup

### Application URL
```
Base URL: http://localhost:8080/api/v1
```

### Headers (for all requests except GET)
```
Content-Type: application/json
```

---

## Test Collections

### 1. Create Product
**Endpoint:** POST `/products`

**Request:**
```json
{
  "name": "Gaming Monitor",
  "description": "4K Gaming Monitor 144Hz",
  "price": 25000.0,
  "imageUrl": "monitor.jpg",
  "category": {
    "id": 1,
    "name": "Electronics"
  }
}
```

**Expected:** 201 Created with product ID

---

### 2. Get All Products
**Endpoint:** GET `/products`

**Expected:** 200 OK with array of all products

---

### 3. Get Single Product
**Endpoint:** GET `/products/1`

**Expected:** 200 OK with product details

---

### 4. Update Product (Full)
**Endpoint:** PUT `/products/1`

**Request:**
```json
{
  "name": "Gaming Mouse Pro",
  "description": "Wireless Gaming Mouse",
  "price": 750.0,
  "imageUrl": "mouse-pro.jpg",
  "category": {
    "id": 1,
    "name": "Electronics"
  }
}
```

**Expected:** 200 OK with updated product

---

### 5. Partial Update Product
**Endpoint:** PATCH `/products/1`

**Request:**
```json
{
  "price": 650.0
}
```

**Expected:** 200 OK with updated product

---

### 6. Filter by Category
**Endpoint:** GET `/products/filter?filterType=category&filterValue=Electronics`

**Expected:** 200 OK with filtered products

---

### 7. Filter by Name
**Endpoint:** GET `/products/filter?filterType=name&filterValue=Mouse`

**Expected:** 200 OK with matching products

---

### 8. Delete Product
**Endpoint:** DELETE `/products/1`

**Expected:** 204 No Content

---

## Error Testing

### 404 Not Found
```
GET /products/9999
```
**Response:**
```json
{
  "timestamp": "2026-05-27T...",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found",
  "path": "/api/v1/products/9999"
}
```

---

### 400 Invalid Input
```
POST /products
{
  "name": "",
  "price": -100
}
```
**Response:**
```json
{
  "timestamp": "2026-05-27T...",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid argument: Product name is required",
  "path": "/api/v1/products"
}
```

---

### 400 Invalid Filter
```
GET /products/filter?filterType=&filterValue=test
```
**Response:**
```json
{
  "timestamp": "2026-05-27T...",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid argument: filterType parameter is required",
  "path": "/api/v1/products/filter"
}
```

---

## Postman Collection JSON

```json
{
  "info": {
    "name": "EcommerceAPI - Task 4",
    "description": "REST API with database persistence"
  },
  "item": [
    {
      "name": "Create Product",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "url": {
          "raw": "http://localhost:8080/api/v1/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "v1", "products"]
        },
        "body": {
          "mode": "raw",
          "raw": "{\"name\":\"Gaming Monitor\",\"description\":\"4K Gaming Monitor\",\"price\":25000.0,\"imageUrl\":\"monitor.jpg\",\"category\":{\"id\":1,\"name\":\"Electronics\"}}"
        }
      }
    },
    {
      "name": "Get All Products",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/v1/products",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "v1", "products"]
        }
      }
    },
    {
      "name": "Get Product by ID",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/v1/products/1",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "v1", "products", "1"]
        }
      }
    },
    {
      "name": "Filter by Category",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/api/v1/products/filter?filterType=category&filterValue=Electronics",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "v1", "products", "filter"],
          "query": [
            {
              "key": "filterType",
              "value": "category"
            },
            {
              "key": "filterValue",
              "value": "Electronics"
            }
          ]
        }
      }
    },
    {
      "name": "Delete Product",
      "request": {
        "method": "DELETE",
        "url": {
          "raw": "http://localhost:8080/api/v1/products/1",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "v1", "products", "1"]
        }
      }
    }
  ]
}
```

---

## Step-by-Step Test Execution

### Test 1: Verify Database Persistence
1. POST Create Product (note the ID)
2. GET All Products (verify product exists)
3. Stop server (Ctrl+C)
4. Restart server (`.\gradlew.bat bootRun`)
5. GET All Products again
6. **Result:** Product still exists ✅

### Test 2: Verify Error Handling
1. GET `/products/9999` → Should return 404 with JSON error
2. POST with empty name → Should return 400 with JSON error
3. GET `/filter?filterType=` → Should return 400 with JSON error

### Test 3: Verify CRUD Operations
1. POST Create (201)
2. GET All (200)
3. PUT Update (200)
4. PATCH Partial Update (200)
5. DELETE (204)
6. GET (404)

### Test 4: Verify Filtering
1. GET `/filter?filterType=category&filterValue=Electronics` → Returns all electronics
2. GET `/filter?filterType=name&filterValue=Mouse` → Returns matching products

---

## Response Times

Expected response times on localhost:
- GET All Products: 50-100ms
- GET Single Product: 30-50ms
- POST Create: 50-100ms
- PUT Update: 50-100ms
- PATCH Partial: 50-100ms
- DELETE: 30-50ms
- Filter Query: 100-150ms

---

## Sample Data

**Pre-loaded on startup:**
```json
{
  "id": 1,
  "name": "Mouse",
  "description": "Gaming Mouse",
  "price": 500.0,
  "imageUrl": "mouse.jpg",
  "category": {"id": 1, "name": "Electronics"}
}

{
  "id": 2,
  "name": "Keyboard",
  "description": "Mechanical Keyboard",
  "price": 1500.0,
  "imageUrl": "keyboard.jpg",
  "category": {"id": 1, "name": "Electronics"}
}
```

---

## Troubleshooting

### Application won't start
- Check if port 8080 is already in use
- Kill process: `Get-Process -Name java | Stop-Process -Force`
- Verify MySQL is running
- Check database credentials in application.yaml

### API returns 404 for valid endpoints
- Verify base URL is correct: `http://localhost:8080/api/v1`
- Check endpoint path spelling
- Verify JSON content-type header

### Database not persisting data
- Verify MySQL connection is active
- Check application logs for errors
- Restart application to reinitialize schema

### Filter returns empty results
- Verify category/product exists first
- Try with exact case-sensitive matching
- Check database directly if unsure

---

## API Documentation

All endpoints are documented in the code with JavaDoc.

**Source Files:**
- `ProductController.java` - Endpoint documentation
- `GlobalExceptionHandler.java` - Error handling documentation
- `ProductService.java` - Business logic documentation
- `ProductRepository.java` - Query documentation
