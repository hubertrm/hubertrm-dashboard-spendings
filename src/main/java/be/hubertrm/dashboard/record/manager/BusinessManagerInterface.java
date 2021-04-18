package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.GenericMapper;
import be.hubertrm.dashboard.record.service.GenericServiceInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface BusinessManagerInterface<DTO, MODEL> {

    default List<DTO> getAll(GenericMapper<MODEL, DTO> mapper, GenericServiceInterface<MODEL> genericServiceInterface, JpaRepository<MODEL, Long> jpa) {
        return mapper.toDtoList(genericServiceInterface.getAll(jpa));
    }

    default DTO getById(Long id, GenericMapper<MODEL, DTO> mapper, GenericServiceInterface<MODEL> genericServiceInterface, JpaRepository<MODEL, Long> jpa)
            throws ResourceNotFoundException {
        return mapper.toDto(genericServiceInterface.getById(id, jpa));
    }

    default DTO createOrUpdate(DTO dto, GenericMapper<MODEL, DTO> mapper, GenericServiceInterface<MODEL> genericServiceInterface, JpaRepository<MODEL, Long> jpa) {
        return mapper.toDto(genericServiceInterface.createOrUpdate(mapper.toEntity(dto), jpa));
    }

    default DTO createOrUpdate(DTO dto, Long id, GenericMapper<MODEL, DTO> mapper, GenericServiceInterface<MODEL> genericServiceInterface, JpaRepository<MODEL, Long> jpa) {
        return createOrUpdate(dto, mapper, genericServiceInterface, jpa);
    }

    default Map<String, Boolean> deleteById(Long id, GenericServiceInterface<MODEL> genericServiceInterface, JpaRepository<MODEL, Long> jpa) throws ResourceNotFoundException {
        return genericServiceInterface.deleteById(id, jpa);
    }
}
