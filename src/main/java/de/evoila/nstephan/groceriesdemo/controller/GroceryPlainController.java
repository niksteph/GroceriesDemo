package de.evoila.nstephan.groceriesdemo.controller;

import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import de.evoila.nstephan.groceriesdemo.repository.GroceryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Plain {@link org.springframework.web.bind.annotation.RestController} without HATEOAS.
 */
@RestController
@RequestMapping("/plain/groceries")
@RequiredArgsConstructor
public class GroceryPlainController {

    private final GroceryItemRepository repo;

    @GetMapping
    public Iterable<GroceryItem> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        // TODO implement
        return null;
    }

    @GetMapping("/{id}")
    public GroceryItem getItem(@PathVariable Long id) {
        // TODO implement
        return null;
    }

    @PostMapping
    public GroceryItem postItem(@RequestBody GroceryItem item) {
        // TODO implement
        return null;
    }

    @PutMapping("/{id}")
    public GroceryItem putItem(@PathVariable Long id, @RequestBody GroceryItem item) {
        // TODO implement
        return null;
    }

    @PatchMapping("/{id}")
    public GroceryItem patchItem(@PathVariable Long id, @RequestBody Map<String, String> itemMap) {
        // TODO implement
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        // TODO implement
    }
}
