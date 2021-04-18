package be.hubertrm.dashboard.record.manager;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import be.hubertrm.dashboard.record.mapper.GenericMapper;
import be.hubertrm.dashboard.record.service.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public class BusinessManager<DTO, MODEL> {

    private GenericMapper<MODEL, DTO> mapper;
    private GenericService<MODEL> genericService;
    private JpaRepository<MODEL, Long> jpa;
    
    public BusinessManager(GenericMapper<MODEL, DTO> mapper,
                           GenericService<MODEL> genericService,
                           JpaRepository<MODEL, Long> jpa) {
        this.mapper = mapper;
        this.genericService = genericService;
        this.jpa = jpa;
    }
    public List<DTO> getAll() {
        return mapper.toDtoList(genericService.getAll());
    }

    public DTO getById(Long id)
            throws ResourceNotFoundException {
        return mapper.toDto(genericService.getById(id));
    }

    public DTO createOrUpdate(DTO dto) {
        return mapper.toDto(genericService.createOrUpdate(mapper.toEntity(dto)));
    }

    public DTO createOrUpdate(DTO dto, Long id) {
        return createOrUpdate(dto);
    }

    public Map<String, Boolean> deleteById(Long id) throws ResourceNotFoundException {
        return genericService.deleteById(id);
    }
}
