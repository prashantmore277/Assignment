package com.assignment.OrderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import com.assignment.OrderService.model.OrderRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost:8080/order"; // Order service URL

    @Test
    void testPlaceOrder_Success() {
        // Valid order request
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItemId("123");  // Assuming stock is available
        orderRequest.setQuantity(10);   // Valid quantity

        // Send POST request to place an order
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, orderRequest, String.class);

        // Assert success response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order confirmed.", response.getBody());
    }

    @Test
    void testPlaceOrder_InsufficientStock() {
        // Order request with a high quantity that exceeds available stock
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItemId("123");  // Assuming stock is available but less than 1000
        orderRequest.setQuantity(10000); // Exceeds stock

        // Send POST request to place an order
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, orderRequest, String.class);

        // Assert insufficient stock response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Insufficient stock.", response.getBody());
    }

    @Test
    void testPlaceOrder_InvalidQuantity() {
        // Order request with invalid quantity
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItemId("123");
        orderRequest.setQuantity(-5);  // Invalid quantity

        // Send POST request to place an order
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, orderRequest, String.class);

        // Assert bad request response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Order quantity must be positive.", response.getBody());
    }

    @Test
    void testPlaceOrder_ItemNotFound() {
        // Order request for an item that doesn't exist
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItemId("999");  // Assuming this ID doesn't exist in inventory
        orderRequest.setQuantity(10);

        // Send POST request to place an order
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, orderRequest, String.class);

        // Assert item not found response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Item with ID not found.", response.getBody());
    }

    @Test
    void testPlaceOrder_InventoryServiceUnreachable() {
        // Simulate inventory service unavailability (you can stop inventory service or mock this)
        // Order request
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItemId("123");
        orderRequest.setQuantity(10);

        // Send POST request to place an order
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, orderRequest, String.class);

        // Assert service unavailable response
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().contains("Inventory Service is not available"));
    }
}
