package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.dto.GroceryItemDTO;
import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import de.evoila.nstephan.groceriesdemo.model.Store;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Mapper(componentModel = "spring")
public abstract class GroceryItemMapper {

    @PersistenceContext
    @Setter
    private EntityManager em;

    @Mapping(source = "storeId", target = "store")
    public abstract GroceryItem dtoToEntity(GroceryItemDTO dto);

    public Store storeFromId(Long storeId) {
        return em.find(Store.class, storeId);
    }
}