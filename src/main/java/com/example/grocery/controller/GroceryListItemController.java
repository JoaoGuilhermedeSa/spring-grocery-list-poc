package com.example.grocery.controller;

import com.example.grocery.model.GroceryList;
import com.example.grocery.model.GroceryListItem;
import com.example.grocery.model.GroceryListStatus;
import com.example.grocery.service.GroceryListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lists")
public class GroceryListItemController {

    private final GroceryListService groceryListService;

    public GroceryListItemController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @PostMapping("/{listId}/items")
    public ResponseEntity<GroceryListItem> addItem(@PathVariable Long listId, @RequestBody GroceryListItem groceryListItem) {
        try {
            GroceryListItem savedItem = groceryListService.addItem(listId, groceryListItem);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{listId}/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long listId, @PathVariable Long itemId) {
        try {
            groceryListService.deleteItem(listId, itemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{listId}/items/{itemId}/status")
    public ResponseEntity<GroceryListItem> updateItemStatus(@PathVariable Long listId, @PathVariable Long itemId, @RequestParam GroceryListStatus status) {
        try {
            GroceryListItem updatedItem = groceryListService.updateItemStatus(listId, itemId, status);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{listId}/redo")
    public ResponseEntity<GroceryList> redoList(@PathVariable Long listId) {
        try {
            GroceryList newList = groceryListService.redoList(listId);
            return new ResponseEntity<>(newList, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
