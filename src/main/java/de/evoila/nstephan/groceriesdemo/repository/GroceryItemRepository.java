package de.evoila.nstephan.groceriesdemo.repository;

import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "groceries", path = "groceries")
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
}
