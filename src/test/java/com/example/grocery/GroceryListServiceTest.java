package com.example.grocery;

import com.example.grocery.model.GroceryList;
import com.example.grocery.model.GroceryListItem;
import com.example.grocery.model.GroceryListItemQuantity;
import com.example.grocery.model.GroceryListStatus;
import com.example.grocery.repository.GroceryListRepository;
import com.example.grocery.service.GroceryListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class GroceryListServiceTest {

    @Autowired
    private GroceryListService groceryListService;

    @Autowired
    private GroceryListRepository groceryListRepository;

    @Test
    public void testRedoList() {
        // Given
        GroceryList originalList = new GroceryList();
        originalList.setTitle("Original List");
        originalList.setItems(Collections.singletonList(new GroceryListItem("Milk", new GroceryListItemQuantity(1.0, null), GroceryListStatus.PENDING)));

        originalList.getItems().forEach(item -> item.setGroceryList(originalList));

        GroceryList savedOriginalList = groceryListService.createList(originalList);

        savedOriginalList.setStatus(GroceryListStatus.DONE);

        groceryListRepository.save(savedOriginalList);

        // When
        GroceryList newList = groceryListService.redoList(savedOriginalList.getId());

        // Then
        assertNotNull(newList);
        assertNotNull(newList.getId());
        assertEquals(savedOriginalList.getTitle(), newList.getTitle());
        assertEquals(GroceryListStatus.PENDING, newList.getStatus());
        assertEquals(savedOriginalList.getItems().size(), newList.getItems().size());
        assertEquals(savedOriginalList.getItems().get(0).getName(), newList.getItems().get(0).getName());
        assertEquals(GroceryListStatus.PENDING, newList.getItems().get(0).getStatus());
    }
}