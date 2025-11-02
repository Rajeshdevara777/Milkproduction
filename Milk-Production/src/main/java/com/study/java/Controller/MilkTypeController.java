// src/main/java/com/study/java/Controller/MilkTypeController.java
package com.study.java.Controller;

import com.study.java.Entity.MilkType;
import com.study.java.Service.MilkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/milk_types")
@CrossOrigin(origins = "*") // keep open during development
public class MilkTypeController {

    private final MilkService service;

    public MilkTypeController(MilkService service) {
        this.service = service;
    }

    // GET /api/milk_types
    @GetMapping
    public List<MilkType> all() {
        return service.findAll();
    }

    // GET /api/milk_types/{id}
    @GetMapping("/{id}")
    public MilkType one(@PathVariable int id) {
        return service.findById(id);
    }

    // POST /api/milk_types
    @PostMapping
    public ResponseEntity<MilkType> create(@RequestBody MilkType body) {
        MilkType saved = service.create(body);
        return ResponseEntity.created(URI.create("/api/milk_types/" + saved.getId())).body(saved);
    }

    // PUT /api/milk_types/{id}
    @PutMapping("/{id}")
    public MilkType update(@PathVariable int id, @RequestBody MilkType body) {
        return service.update(id, body);
    }

    // DELETE /api/milk_types/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
