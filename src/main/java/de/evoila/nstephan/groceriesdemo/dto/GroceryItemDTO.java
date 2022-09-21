package de.evoila.nstephan.groceriesdemo.dto;

import lombok.Data;

@Data
public class GroceryItemDTO {

    private Long id;

    private String description;

    private String amount;

    private String comment;

    private Long storeId;
}
