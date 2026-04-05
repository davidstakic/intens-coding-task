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

import com.example.intens.dto.CreateSkillDTO;
import com.example.intens.dto.SkillDTO;
import com.example.intens.dto.UpdateSkillDTO;
import com.example.intens.service.SkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin
public class SkillController {

	@Autowired
	private SkillService service;
	
	
	@PostMapping
	public ResponseEntity<SkillDTO> create(@Valid @RequestBody CreateSkillDTO dto) {
        return ResponseEntity.ok(service.create(dto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SkillDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<SkillDTO>> searchSkills(
	        @RequestParam String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
	    return ResponseEntity.ok(service.searchByName(name, page, size));
	}
	
	@PutMapping
	public ResponseEntity<SkillDTO> update(@Valid @RequestBody UpdateSkillDTO dto) {
        return ResponseEntity.ok(service.update(dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
        return ResponseEntity.noContent().build();
	}

}
