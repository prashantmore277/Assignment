# Microservices Documentation

## Overview
This document contains the details of two microservices developed, their endpoints, JSON payload formats, inter-service API calls, and instructions for running and testing the services. 

---

## Microservice 1: **Inventory Service**

### Endpoints

#### 1. Add Inventory
- **URL**: `POST /inventory/add`
- **Description**: Adds a new inventory item.
- **Request Payload**:
  ```json
  {
    "itemId": "123",
    "name": "pineapple",
    "stock": 1000
  }
  ```
- **Response**:
  ```json
  {
    "itemId": "123",
    "name": "pineapple",
    "stock": 1000
  }
  ```
- **Status Codes**:
  - `201 Created`
  - `400 Bad Request`

#### 2. Get Inventory by ID
- **URL**: `GET /inventory/{itemId}`
- **Description**: Fetches inventory details by item ID.
- **Response**:
  ```json
  {
    "itemId": "123",
    "name": "pineapple",
    "stock": 1000
  }
  ```
- **Status Codes**:
  - `200 OK`
  - `404 Not Found`

#### 3. Update Stock
- **URL**: `PUT /inventory/{itemId}`
- **Description**: Updates the stock for an inventory item.
- **Request Payload**:
  ```json
  {
    "quantity": 500
  }
  ```
- **Response**:
  ```json
  {
    "itemId": "123",
    "name": "pineapple",
    "stock": 1500
  }
  ```
- **Status Codes**:
  - `200 OK`
  - `404 Not Found`

#### 4. Delete Inventory
- **URL**: `DELETE /inventory/{itemId}`
- **Description**: Deletes an inventory item by ID.
- **Status Codes**:
  - `204 No Content`
  - `404 Not Found`

---

## Microservice 2: **Order Service**

### Endpoints

#### 1. Create Order
- **URL**: `POST /order/create`
- **Description**: Creates a new order.
- **Request Payload**:
  ```json
  {
    "orderId": "ORD001",
    "itemId": "123",
    "quantity": 100
  }
  ```
- **Response**:
  ```json
  {
    "orderId": "ORD001",
    "itemId": "123",
    "quantity": 100,
    "status": "Order Placed"
  }
  ```
- **Status Codes**:
  - `201 Created`
  - `400 Bad Request`

#### 2. Get Order by ID
- **URL**: `GET /order/{orderId}`
- **Description**: Fetches details of an order by order ID.
- **Response**:
  ```json
  {
    "orderId": "ORD001",
    "itemId": "123",
    "quantity": 100,
    "status": "Order Placed"
  }
  ```
- **Status Codes**:
  - `200 OK`
  - `404 Not Found`

#### 3. Cancel Order
- **URL**: `PUT /order/cancel/{orderId}`
- **Description**: Cancels an order by order ID.
- **Response**:
  ```json
  {
    "orderId": "ORD001",
    "status": "Cancelled"
  }
  ```
- **Status Codes**:
  - `200 OK`
  - `404 Not Found`

---

## API Calls Between Services

### Inventory Validation (Order Service → Inventory Service)
- **URL**: `GET /inventory/{itemId}`
- **Description**: Checks if the inventory item exists and has sufficient stock.
- **Request from Order Service**:
  ```http
  GET http://inventory-service:8081/inventory/123
  ```
- **Response**:
  ```json
  {
    "itemId": "123",
    "name": "pineapple",
    "stock": 1000
  }
  ```
- **Status Codes**:
  - `200 OK`
  - `404 Not Found`

### Update Stock (Order Service → Inventory Service)
- **URL**: `PUT /inventory/{itemId}`
- **Description**: Updates the stock after order creation.
- **Request from Order Service**:
  ```http
  PUT http://inventory-service:8081/inventory/123
  {
    "quantity": -100
  }
  ```
- **Response**:
  ```json
  {
    "itemId": "123",
    "name": "pineapple",
    "stock": 900
  }
  ```
- **Status Codes**:
  - `200 OK`
  - `404 Not Found`

---

## Instructions to Run Services

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- IDE (e.g., Eclipse or IntelliJ IDEA)

### Running the Services
1. **Clone the Repository**:
   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```
2. **Build the Services**:
   ```sh
   mvn clean install
   ```
3. **Run the Services**:
   - For Inventory Service:
     ```sh
     mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
     ```
   - For Order Service:
     ```sh
     mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
     ```

---

## Instructions for Testing

### Running Tests
1. **Using Maven**:
   ```sh
   mvn test
   ```
2. **Using IDE**:
   - Navigate to the test file in your IDE.
   - Right-click and select `Run as JUnit Test` (or equivalent option).

### Test File Names
- Inventory Service: `InventoryControllerIntegrationTest`
- Order Service: `OrderControllerIntegrationTest`

### Example Test Command
```sh
mvn -Dtest=InventoryControllerIntegrationTest test
```

---

## API Documentation

### By Adding Swagger API Dependacy in pom file you can use Swagger UI for API Documentation

### Endpoint for Swagger UI
- **Inventory Service**: `http://localhost:8081/swagger-ui.html`
- **Order Service**: `http://localhost:8080/swagger-ui.html`

---
