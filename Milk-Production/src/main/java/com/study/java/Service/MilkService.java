// src/main/java/com/study/java/Service/MilkService.java
package com.study.java.Service;

import com.study.java.Entity.MilkType;
import com.study.java.Repository.MilkTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class MilkService {

    private final MilkTypeRepository repo;

    public MilkService(MilkTypeRepository repo) {
        this.repo = repo;
    }

    // List all milk types
    public List<MilkType> findAll() {
        return repo.findAll();
    }

    // Get one by id
    public MilkType findById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "MilkType not found: " + id));
    }

    // Create
    public MilkType create(MilkType body) {
        body.setId(0); // ensure insert
        return repo.save(body);
    }

    // Update
    public MilkType update(int id, MilkType body) {
        MilkType existing = findById(id);
        existing.setName(body.getName());
        existing.setFatPercentage(body.getFatPercentage());
        existing.setPricePerLiter(body.getPricePerLiter());
        return repo.save(existing);
    }

    // Delete
    public void delete(int id) {
        MilkType existing = findById(id);
        repo.delete(existing);
    }
}

