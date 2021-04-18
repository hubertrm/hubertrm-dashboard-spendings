package be.hubertrm.dashboard.record.service;

import be.hubertrm.dashboard.record.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericService<MODEL> {

    private JpaRepository<MODEL, Long> jpa;

    public GenericService(JpaRepository<MODEL, Long> jpa) {
        this.jpa = jpa;
    }
    public List<MODEL> getAll() {
        return jpa.findAll();
    }

    public MODEL getById(Long id) throws ResourceNotFoundException {
        return jpa.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error"+id));
    }

    public MODEL createOrUpdate(MODEL model) {
        return jpa.save(model);
    }

    public Map<String, Boolean> deleteById(Long id) throws ResourceNotFoundException {
        MODEL model = jpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find element from"+id));

        jpa.delete(model);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
