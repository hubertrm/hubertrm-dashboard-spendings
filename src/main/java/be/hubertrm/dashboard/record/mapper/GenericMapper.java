package be.hubertrm.dashboard.record.mapper;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * The Interface GenericMapper provides the methods for mapping entities to data transfer objects and back.
 * @param <E> the element type of the entity object
 * @param <D> the generic type of the data transfer object
 */
public interface GenericMapper<E, D> {

    /**
     * Maps the given entity object to a data transfer object
     * @param entity the entity object
     * @return the data transfer object
     */
    D toDto(E entity) throws ResourceNotFoundException;

    /**
     * Maps the given collection of entity objects to a list of data transfer objects
     * @param entities the collection of entity objects
     * @return the list of data transfer objects
     */
    List<D> toDtoList(Collection<E> entities);

    /**
     * Maps the given data transfer object to an entity object
     * @param dto the data transfer object
     * @return the entity object
     */
    E toEntity(D dto);

    /**
     * Maps the given collection of entity objects to a list of data transfer objects
     * @param dtoList the collection of data transfer objects
     * @return the list of entity objects
     */
    List<E> toEntityList(Collection<D> dtoList);
}
