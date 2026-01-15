package com.example.grocery.repository;

import com.example.grocery.model.GroceryListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryListItemRepository extends JpaRepository<GroceryListItem, Long> {
}
