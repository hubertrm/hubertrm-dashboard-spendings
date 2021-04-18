package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GenericServiceInterface<MODEL> {

    default List<MODEL> getAll(JpaRepository<MODEL, Long> jpa) {
        return jpa.findAll();
    }

    default MODEL getById(Long id, JpaRepository<MODEL, Long> jpa) throws ResourceNotFoundException {
        return jpa.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error"+id));
    }

    default MODEL createOrUpdate(MODEL model, JpaRepository<MODEL, Long> jpa) {
        return jpa.save(model);
    }

    default Map<String, Boolean> deleteById(Long id, JpaRepository<MODEL, Long> jpa) throws ResourceNotFoundException {
        MODEL model = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find element from"+id));

        jpa.delete(model);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
