package com.example.grocery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GroceryListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private GroceryListItemQuantity quantity;

    @Enumerated(EnumType.STRING)
    private GroceryListStatus status;

    @ManyToOne
    @JoinColumn(name = "grocery_list_id")
    @JsonIgnore
    private GroceryList groceryList;

    public GroceryListItem() {
    }

    public GroceryListItem(String name, GroceryListItemQuantity quantity, GroceryListStatus status) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroceryListStatus getStatus() {
        return status;
    }

    public void setStatus(GroceryListStatus status) {
        this.status = status;
    }

    public GroceryList getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    public GroceryListItemQuantity getQuantity() { return quantity; }

    public void setQuantity(GroceryListItemQuantity quantity) { this.quantity = quantity; }
}