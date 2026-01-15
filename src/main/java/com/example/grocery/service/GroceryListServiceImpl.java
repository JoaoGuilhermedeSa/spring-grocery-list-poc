package com.example.grocery.service;

import com.example.grocery.model.GroceryList;
import com.example.grocery.model.GroceryListItem;
import com.example.grocery.model.GroceryListStatus;
import com.example.grocery.repository.GroceryListItemRepository;
import com.example.grocery.repository.GroceryListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final GroceryListItemRepository groceryListItemRepository;

    public GroceryListServiceImpl(GroceryListRepository groceryListRepository, GroceryListItemRepository groceryListItemRepository) {
        this.groceryListRepository = groceryListRepository;
        this.groceryListItemRepository = groceryListItemRepository;
    }

    @Override
    public GroceryList createList(GroceryList groceryList) {
        groceryList.setStatus(GroceryListStatus.PENDING);
        return groceryListRepository.save(groceryList);
    }

    @Override
    public List<GroceryList> getAllLists() {
        return groceryListRepository.findAll();
    }

    @Override
    public Optional<GroceryList> getListById(Long listId) {
        return groceryListRepository.findById(listId);
    }

    @Override
    public GroceryListItem addItem(Long listId, GroceryListItem groceryListItem) {
        return groceryListRepository.findById(listId).map(list -> {
            if (list.getStatus() == GroceryListStatus.DONE) {
                throw new RuntimeException("Cannot add items to a completed list.");
            }
            groceryListItem.setGroceryList(list);
            groceryListItem.setStatus(GroceryListStatus.PENDING);
            return groceryListItemRepository.save(groceryListItem);
        }).orElseThrow(() -> new RuntimeException("Grocery list not found."));
    }

    @Override
    public void deleteItem(Long listId, Long itemId) {
        GroceryList list = groceryListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Grocery list not found."));

        if (list.getStatus() == GroceryListStatus.DONE) {
            throw new RuntimeException("Cannot delete items from a completed list.");
        }

        groceryListItemRepository.findById(itemId).ifPresent(item -> {
            if (item.getGroceryList().getId().equals(listId)) {
                groceryListItemRepository.delete(item);
            } else {
                throw new RuntimeException("Item does not belong to the specified list.");
            }
        });
    }

    @Override
    public GroceryListItem updateItemStatus(Long listId, Long itemId, GroceryListStatus status) {
        GroceryList list = groceryListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Grocery list not found."));

        if (list.getStatus() == GroceryListStatus.DONE) {
            throw new RuntimeException("Cannot update items in a completed list.");
        }

        GroceryListItem updatedItem = groceryListItemRepository.findById(itemId).map(item -> {
            if (item.getGroceryList().getId().equals(listId)) {
                item.setStatus(status);
                return groceryListItemRepository.save(item);
            } else {
                throw new RuntimeException("Item does not belong to the specified list.");
            }
        }).orElseThrow(() -> new RuntimeException("Grocery list item not found."));

        checkAndUpdateListStatus(list);

        return updatedItem;
    }

    private void checkAndUpdateListStatus(GroceryList list) {
        boolean allItemsDone = list.getItems().stream()
                .allMatch(item -> item.getStatus() == GroceryListStatus.DONE);

        if (allItemsDone) {
            list.setStatus(GroceryListStatus.DONE);
            groceryListRepository.save(list);
        }
    }

    @Override
    @Transactional
    public GroceryList redoList(Long listId) {
        GroceryList originalList = groceryListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Grocery list not found."));

        if (originalList.getStatus() != GroceryListStatus.DONE) {
            throw new RuntimeException("Only completed lists can be re-done.");
        }

        GroceryList newList = new GroceryList();
        newList.setTitle(originalList.getTitle());
        newList.setStatus(GroceryListStatus.PENDING);

        List<GroceryListItem> newItems = originalList.getItems().stream().map(item -> {
            GroceryListItem newItem = new GroceryListItem();
            newItem.setName(item.getName());
            newItem.setQuantity(item.getQuantity());
            newItem.setStatus(GroceryListStatus.PENDING);
            newItem.setGroceryList(newList);
            return newItem;
        }).toList();

        newList.setItems(newItems);

        return groceryListRepository.save(newList);
    }
}