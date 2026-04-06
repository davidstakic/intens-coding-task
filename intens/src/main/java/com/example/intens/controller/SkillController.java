package com.example.intens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.intens.dto.CreateSkillDTO;
import com.example.intens.dto.SkillDTO;
import com.example.intens.dto.UpdateSkillDTO;
import com.example.intens.service.SkillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin
@Tag(name = "Skills", description = "Skill management APIs")
public class SkillController {

    @Autowired
    private SkillService service;
    

    @Operation(summary = "Create a new skill")
    @PostMapping
    public ResponseEntity<SkillDTO> create(@Valid @RequestBody CreateSkillDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get skill by id")
    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getById(
            @Parameter(description = "Skill id") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Search skills by name with pagination")
    @GetMapping("/search")
    public ResponseEntity<Page<SkillDTO>> search(
            @Parameter(description = "Skill name") @RequestParam String name,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(service.search(name, page, size));
    }

    @Operation(summary = "Update existing skill")
    @PutMapping
    public ResponseEntity<SkillDTO> update(@Valid @RequestBody UpdateSkillDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @Operation(summary = "Delete skill by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Skill id") @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}