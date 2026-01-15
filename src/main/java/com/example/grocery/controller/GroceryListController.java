package com.example.grocery.controller;

import com.example.grocery.model.GroceryList;
import com.example.grocery.service.GroceryListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lists")
public class GroceryListController {

    private final GroceryListService groceryListService;

    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @PostMapping
    public ResponseEntity<GroceryList> createList(@RequestBody GroceryList groceryList) {
        GroceryList savedList = groceryListService.createList(groceryList);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    @GetMapping
    public List<GroceryList> getAllLists() {
        return groceryListService.getAllLists();
    }
}
