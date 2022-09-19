package de.evoila.nstephan.groceriesdemo.controller;

import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import de.evoila.nstephan.groceriesdemo.repository.GroceryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

/**
 * Plain {@link org.springframework.web.bind.annotation.RestController} without HATEOAS.
 */
@RestController
@RequestMapping("/plain/groceries")
@RequiredArgsConstructor
public class GroceryPlainController {

    private final GroceryItemRepository repo;

    @GetMapping
    public Iterable<GroceryItem> getAll(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Page<GroceryItem> groceryItemPage;
        if (page == null || size == null)
            groceryItemPage = repo.findAll(Pageable.unpaged());
        else
            groceryItemPage = repo.findAll(PageRequest.of(page, size));
        return groceryItemPage.getContent();
    }

    @GetMapping("/{id}")
    public GroceryItem getItem(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public GroceryItem postItem(@RequestBody GroceryItem item) {
        return repo.save(item);
    }

    @PutMapping("/{id}")
    public GroceryItem putItem(@PathVariable Long id, @RequestBody GroceryItem item) {
        if (item.getId() == null)
            item.setId(id);
        if (!Objects.equals(id, item.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Request body contains an id that does not match the id specified in the path.");
        if (!repo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return repo.save(item);
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
