package com.example.grocery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private GroceryListStatus status;

    @OneToMany(mappedBy = "groceryList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroceryListItem> items = new ArrayList<>();

    public GroceryList() {
    }

    public GroceryList(String title, GroceryListStatus status) {
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GroceryListStatus getStatus() {
        return status;
    }

    public void setStatus(GroceryListStatus status) {
        this.status = status;
    }

    public List<GroceryListItem> getItems() {
        return items;
    }

    public void setItems(List<GroceryListItem> items) {
        this.items = items;
    }
}
