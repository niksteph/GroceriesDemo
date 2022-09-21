package de.evoila.nstephan.groceriesdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class GroceryItem extends BaseEntity {

    private String description;

    private String amount;

    private String comment;

    @ManyToOne
    private Store store;

    //TODO optional: category
}
