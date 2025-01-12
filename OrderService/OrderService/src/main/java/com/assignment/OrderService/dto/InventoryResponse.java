package com.assignment.OrderService.dto;

public class InventoryResponse {
	private String itemId;

	private int stock;

	// Getters and Setters

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}