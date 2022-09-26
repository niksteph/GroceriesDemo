package de.evoila.nstephan.groceriesdemo.dto;

import lombok.Data;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class GroceryItemPatchDTO {

    private Long id;

    private String description;

    private JsonNullable<String> amount;

    private JsonNullable<String> comment;

    private JsonNullable<Long> storeId;
}
