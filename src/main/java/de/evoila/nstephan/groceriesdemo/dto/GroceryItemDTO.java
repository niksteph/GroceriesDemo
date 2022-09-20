package de.evoila.nstephan.groceriesdemo.dto;

import de.evoila.nstephan.groceriesdemo.model.GroceryItem;
import lombok.Data;

@Data
public class GroceryItemDTO extends DTO {

    private Long id;

    private String description;

    private String amount;

    private String comment;

    private Long storeId;

    @Override
    public GroceryItem entity() {
        return null;
    }

    @Override
    public GroceryItem entity(Long id) {
        return null;
    }
}
