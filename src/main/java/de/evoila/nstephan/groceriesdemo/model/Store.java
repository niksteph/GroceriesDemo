package de.evoila.nstephan.groceriesdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private UUID uuid;

    @Getter
    @Setter
    private String name;
}
