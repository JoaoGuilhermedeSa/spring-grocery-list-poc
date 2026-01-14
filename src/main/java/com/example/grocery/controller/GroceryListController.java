package com.example.grocery.controller;

import com.example.grocery.model.GroceryList;
import com.example.grocery.model.GroceryListStatus;
import com.example.grocery.repository.GroceryListRepository;
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

    private final GroceryListRepository groceryListRepository;

    public GroceryListController(GroceryListRepository groceryListRepository) {
        this.groceryListRepository = groceryListRepository;
    }

    @PostMapping
    public ResponseEntity<GroceryList> createList(@RequestBody GroceryList groceryList) {
        groceryList.setStatus(GroceryListStatus.PENDING);
        GroceryList savedList = groceryListRepository.save(groceryList);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    @GetMapping
    public List<GroceryList> getAllLists() {
        return groceryListRepository.findAll();
    }
}
