package com.assignment.OrderService.dto;

public class InventoryUpdateRequest {
	private int quantity;

	public InventoryUpdateRequest(int quantity) {
		this.quantity = quantity;
	}

	// Getters and Setters
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}