package com.assignment.InventoryService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.InventoryService.customException.*;
import com.assignment.InventoryService.model.Inventory;
import com.assignment.InventoryService.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;


 // Get inventory details by item ID
    @GetMapping("/{itemId}")
    public ResponseEntity<Inventory> getInventory(@PathVariable String itemId) {
        try {
            Inventory inventory = service.getInventory(itemId);
            return new ResponseEntity<>(inventory, HttpStatus.OK);
        } catch (InventoryNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Item not found
        }
    }

    // Get all inventory items
    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventoryList = service.getAllInventory();
        return new ResponseEntity<>(inventoryList, HttpStatus.OK);
    }

    // Update stock for an item
    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateStock(@PathVariable String itemId, @RequestBody Map<String, Integer> payload) {
        service.updateStock(itemId, payload.get("quantity"));
        return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
    }                

    // Add a new inventory item
    @PostMapping("/add")
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = service.addInventory(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    // Delete an inventory item by item ID
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String itemId) {
        service.deleteInventory(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Exception handler for insufficient stock
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientStockException(InsufficientStockException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
