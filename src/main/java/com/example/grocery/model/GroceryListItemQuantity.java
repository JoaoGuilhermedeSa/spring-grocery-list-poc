package com.example.grocery.model;

import com.example.grocery.enums.QuantityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Embeddable
public class GroceryListItemQuantity {

    private Double amount;

    private QuantityType type;

    public GroceryListItemQuantity(Double amount, QuantityType type) {
        this.amount = amount;
        this.type = type;
    }

    public GroceryListItemQuantity() {

    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public QuantityType getType() {
        return type;
    }

    public void setType(QuantityType type) {
        this.type = type;
    }
}
