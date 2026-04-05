package com.example.intens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.intens.dto.CandidateDTO;
import com.example.intens.dto.CreateCandidateDTO;
import com.example.intens.dto.UpdateCandidateDTO;
import com.example.intens.service.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin
public class CandidateController {

	@Autowired
	private CandidateService service;
	
	
	@PostMapping
    public ResponseEntity<CandidateDTO> create(@Valid @RequestBody CreateCandidateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<CandidateDTO>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String skill,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(service.search(name, skill, page, size));
    }

    @PutMapping
    public ResponseEntity<CandidateDTO> update(@Valid @RequestBody UpdateCandidateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
