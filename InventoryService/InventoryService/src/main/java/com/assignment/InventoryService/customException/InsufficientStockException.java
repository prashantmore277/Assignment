package com.assignment.InventoryService.customException;

public class InsufficientStockException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -461357404334807623L;

	public InsufficientStockException(String message) {
        super(message);
    }
}
