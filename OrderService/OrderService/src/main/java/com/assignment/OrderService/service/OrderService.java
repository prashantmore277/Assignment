package com.assignment.OrderService.service;

import com.assignment.OrderService.model.OrderRequest;
import com.assignment.OrderService.dto.InventoryResponse;
import com.assignment.OrderService.dto.InventoryUpdateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

@Service
public class OrderService {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String placeOrder(OrderRequest orderRequest) {
        // URL to check the stock for an item
        String inventoryUrl = inventoryServiceUrl + "/inventory/" + orderRequest.getItemId();
        
        try {
            // Call Inventory Service to get stock details for the item
            ResponseEntity<InventoryResponse> response = restTemplate.getForEntity(inventoryUrl, InventoryResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                InventoryResponse inventoryResponse = response.getBody();
                int availableStock = inventoryResponse.getStock();

                // Check if sufficient stock is available
                if (availableStock >= orderRequest.getQuantity()) {
                    // Only update stock if the quantity is positive and valid
                    if (orderRequest.getQuantity() > 0) {
                        // Decrease the stock after the order is confirmed
                        String updateStockUrl = inventoryServiceUrl + "/inventory/" + orderRequest.getItemId();
                        restTemplate.put(updateStockUrl, new InventoryUpdateRequest(-orderRequest.getQuantity()));
                        return "Order confirmed.";
                    } else {
                        return "Order quantity must be positive.";
                    }
                } else {
                    return "Insufficient stock.";
                }
            } else {
                return "Failed to fetch inventory details.";
            }

        } catch (HttpClientErrorException.NotFound e) {
            // Handling 404 Not Found specifically
            return "Item with ID not found.";
        } catch (ResourceAccessException e) {
            // Handling when the Inventory service is unreachable
            return "Inventory Service is not available. Please try again later.";
        } catch (Exception e) {
            // General exception handler for any other errors
            return "An error occurred while placing the order: " + e.getMessage();
        }
    }
}
