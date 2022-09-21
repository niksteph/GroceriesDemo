package de.evoila.nstephan.groceriesdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Store extends BaseEntity {

    private String name;
}
