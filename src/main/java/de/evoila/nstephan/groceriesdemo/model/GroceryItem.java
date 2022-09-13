package de.evoila.nstephan.groceriesdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private UUID uuid;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String amount;

    @Getter
    @Setter
    private String comment;

    @ManyToOne
    @Getter
    @Setter
    private Store store;

    //TODO optional: category
}
