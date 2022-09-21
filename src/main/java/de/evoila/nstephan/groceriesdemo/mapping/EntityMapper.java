package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.model.BaseEntity;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Mapper(componentModel = "spring")
public abstract class EntityMapper {

    @PersistenceContext
    @Setter
    private EntityManager em;

    public <T extends BaseEntity> T entityFromId(Long id, @TargetType Class<T> clazz) {
        return em.find(clazz, id);
    }
}
