package com.example.grocery.service;

import com.example.grocery.model.GroceryList;
import com.example.grocery.model.GroceryListItem;
import com.example.grocery.model.GroceryListStatus;

import java.util.List;
import java.util.Optional;

public interface GroceryListService {
    GroceryList createList(GroceryList groceryList);

    List<GroceryList> getAllLists();

    Optional<GroceryList> getListById(Long listId);

    GroceryListItem addItem(Long listId, GroceryListItem groceryListItem);

    void deleteItem(Long listId, Long itemId);

    GroceryListItem updateItemStatus(Long listId, Long itemId, GroceryListStatus status);

    GroceryList redoList(Long listId);
}
