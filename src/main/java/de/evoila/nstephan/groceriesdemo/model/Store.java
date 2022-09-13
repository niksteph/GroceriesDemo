package de.evoila.nstephan.groceriesdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String name;
}
