package de.evoila.nstephan.groceriesdemo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
}
