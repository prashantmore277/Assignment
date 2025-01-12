package com.assignment.InventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.InventoryService.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
}
