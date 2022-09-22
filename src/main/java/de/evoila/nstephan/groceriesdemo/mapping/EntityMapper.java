package de.evoila.nstephan.groceriesdemo.mapping;

import de.evoila.nstephan.groceriesdemo.model.BaseEntity;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Mapper for mapping id to any {@link BaseEntity}
 */
@Mapper(componentModel = "spring")
public abstract class EntityMapper {

    @PersistenceContext
    @Setter
    private EntityManager em;

    /**
     * Maps an id to a {@link BaseEntity}
     *
     * @param id    identifier
     * @param clazz type of the target object
     * @param <T>   any subclass of {@link BaseEntity}
     * @return an object with a subclass of {@link BaseEntity}
     */
    public <T extends BaseEntity> T entityFromId(Long id, @TargetType Class<T> clazz) {
        return em.find(clazz, id);
    }
}
