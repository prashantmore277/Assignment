package com.assignment.InventoryService;

import com.assignment.InventoryService.model.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost:8081/inventory";
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        // Initialize inventory object
        inventory = new Inventory();
        inventory.setItemId("123");
        inventory.setName("pineapple");
        inventory.setStock(1000);
    }

    @AfterEach
    void tearDown() {
        // Clean up inventory state after each test
        restTemplate.delete(baseUrl + "/123");  // Delete the inventory item after each test to ensure consistency
    }

    @Test
    void testAddInventory() {
        // Send POST request to add inventory
        ResponseEntity<Inventory> addResponse = restTemplate.postForEntity(baseUrl + "/add", inventory, Inventory.class);

        // Assert that the status code is CREATED (201)
        assertEquals(HttpStatus.CREATED, addResponse.getStatusCode());
    }

    @Test
    void testGetInventory() {
        // First add the inventory to ensure it's present
        restTemplate.postForEntity(baseUrl + "/add", inventory, Inventory.class);

        // Get the inventory by ID using GET request
        ResponseEntity<Inventory> getResponse = restTemplate.getForEntity(baseUrl + "/123", Inventory.class);

        // Assert that the status code is OK (200)
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }

    @Test
    void testUpdateStock() {
        // First add the inventory to ensure it's present
        restTemplate.postForEntity(baseUrl + "/add", inventory, Inventory.class);

        // Update stock by sending PUT request
        restTemplate.put(baseUrl + "/123", "{ \"quantity\": 500 }", String.class);

        // Get the updated inventory using GET request
        ResponseEntity<Inventory> getResponse = restTemplate.getForEntity(baseUrl + "/123", Inventory.class);

        // Assert that the stock is updated correctly
        assertEquals(1000, getResponse.getBody().getStock());
    }

    @Test
    void testDeleteInventory() {
        // First add the inventory to ensure it's present
        restTemplate.postForEntity(baseUrl + "/add", inventory, Inventory.class);

        // Delete inventory by sending DELETE request
        restTemplate.delete(baseUrl + "/123");

        // Verify the item is deleted by attempting to get it
        ResponseEntity<Inventory> getResponse = restTemplate.getForEntity(baseUrl + "/123", Inventory.class);

        // Assert that the status code is NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
