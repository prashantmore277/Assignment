package com.assignment.OrderService.controller;

import com.assignment.OrderService.model.OrderRequest;
import com.assignment.OrderService.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        String result = orderService.placeOrder(orderRequest);
        if (orderRequest.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body("Order quantity must be positive.");
        }
        // Check if the result is an error message and return an appropriate status
        if (result.contains("Order confirmed")) {
            return ResponseEntity.ok(result); // 200 OK with success message
        } else if (result.contains("Insufficient stock")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // 400 Bad Request
        } else if (result.contains("Inventory Service is not available")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result); // 503 Service Unavailable
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result); // 500 Internal Server Error
        }
    }
}
