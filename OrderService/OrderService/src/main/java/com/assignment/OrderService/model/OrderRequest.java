package com.assignment.OrderService.model;

public class OrderRequest {
    private String itemId;
    
	private int quantity;

    // Getters and Setters
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}