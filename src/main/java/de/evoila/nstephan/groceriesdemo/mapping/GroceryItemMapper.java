package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.dto.GroceryItemDTO;
import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for mapping between {@link GroceryItem} and {@link GroceryItemDTO}.
 */
@Mapper(componentModel = "spring", uses = EntityMapper.class)
public interface GroceryItemMapper {

    /**
     * Maps {@link GroceryItemDTO} to {@link GroceryItem}
     *
     * @param dto
     * @return the entity
     */
    @Mapping(source = "storeId", target = "store")
    GroceryItem dtoToEntity(GroceryItemDTO dto);
}