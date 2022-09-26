package de.evoila.nstephan.groceriesdemo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class GroceryItem extends BaseEntity {

    private String description;

    private String amount;

    private String comment;

    @ManyToOne
    private Store store;
}
