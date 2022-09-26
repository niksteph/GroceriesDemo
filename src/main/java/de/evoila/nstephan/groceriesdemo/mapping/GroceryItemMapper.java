package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.dto.GroceryItemDTO;
import de.evoila.nstephan.groceriesdemo.dto.GroceryItemPatchDTO;
import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import org.mapstruct.*;

/**
 * Mapper for mapping between {@link GroceryItem} and it's DTOs.
 */
@Mapper(componentModel = "spring", uses = { EntityMapper.class, JsonNullableMapper.class })
public interface GroceryItemMapper {

    /**
     * Maps {@link GroceryItemDTO} to {@link GroceryItem}
     *
     * @param dto
     * @return the entity
     */
    @Mapping(source = "storeId", target = "store")
    GroceryItem dtoToEntity(GroceryItemDTO dto);

    /**
     * Maps {@link GroceryItemPatchDTO} to {@link GroceryItem}.
     * Used for JSON Merge Patch
     *
     * @param dto
     * @return the entity
     */
    @Mapping(source = "storeId", target = "store")
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget GroceryItem item, GroceryItemPatchDTO dto);
}