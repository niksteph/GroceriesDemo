package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.dto.GroceryItemDTO;
import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroceryItemMapper {

    GroceryItem dtoToEntity(GroceryItemDTO dto);
}
