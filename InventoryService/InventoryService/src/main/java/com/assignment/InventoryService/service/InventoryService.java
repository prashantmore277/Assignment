package com.assignment.InventoryService.service;

import com.assignment.InventoryService.model.Inventory;
import com.assignment.InventoryService.repository.InventoryRepository;
import com.assignment.InventoryService.customException.InventoryNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    // Get inventory details
    public Inventory getInventory(String itemId) {
        return repository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Item not found with ID: " + itemId));
    }
    
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    // Update stock for an inventory item
    public void updateStock(String itemId, int quantity) {
        Inventory inventory = repository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Item not found with ID: " + itemId));

        int updatedStock = inventory.getStock() + quantity;

        if (updatedStock < 0) {
            throw new IllegalArgumentException("Insufficient stock for item ID: " + itemId);
        }

        inventory.setStock(updatedStock);
        repository.save(inventory);
    }
    
 // Update stock for an inventory item
    public void updateStockQuantity(String itemId, int quantity) {
        Inventory inventory = repository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Item not found with ID: " + itemId));

        if (quantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for item ID: " + itemId);
        }

        inventory.setStock(quantity);
        repository.save(inventory);
    }
    

    // Add a new inventory item
    public Inventory addInventory(Inventory inventory) {
        if (repository.existsById(inventory.getItemId())) {
            throw new IllegalArgumentException("Item already exists with ID: " + inventory.getItemId());
        }
        return repository.save(inventory);
    }

    // Delete an inventory item
    public void deleteInventory(String itemId) {
        if (!repository.existsById(itemId)) {
            throw new InventoryNotFoundException("Item not found with ID: " + itemId);
        }
        repository.deleteById(itemId);
    }
}
