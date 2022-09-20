package de.evoila.nstephan.groceriesdemo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String amount;

    private String comment;

    @ManyToOne
    private Store store;

    //TODO optional: category
}
