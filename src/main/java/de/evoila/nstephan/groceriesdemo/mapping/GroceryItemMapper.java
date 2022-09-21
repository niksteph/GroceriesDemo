package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.dto.GroceryItemDTO;
import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = EntityMapper.class)
public interface GroceryItemMapper {

    @Mapping(source = "storeId", target = "store")
    GroceryItem dtoToEntity(GroceryItemDTO dto);
}