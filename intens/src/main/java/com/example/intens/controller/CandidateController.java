package com.example.intens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.intens.dto.CandidateDTO;
import com.example.intens.dto.CreateCandidateDTO;
import com.example.intens.dto.UpdateCandidateDTO;
import com.example.intens.service.CandidateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin
@Tag(name = "Candidates", description = "Candidate management APIs")
public class CandidateController {

    @Autowired
    private CandidateService service;
    

    @Operation(summary = "Create a new candidate")
    @PostMapping
    public ResponseEntity<CandidateDTO> create(@Valid @RequestBody CreateCandidateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Get candidate by id")
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getById(
            @Parameter(description = "Candidate id") @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Search candidates by name and skill with pagination")
    @GetMapping("/search")
    public ResponseEntity<Page<CandidateDTO>> search(
            @Parameter(description = "Candidate name") @RequestParam(required = false) String name,
            @Parameter(description = "Skill name") @RequestParam(required = false) String skill,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(service.search(name, skill, page, size));
    }

    @Operation(summary = "Update existing candidate")
    @PutMapping
    public ResponseEntity<CandidateDTO> update(@Valid @RequestBody UpdateCandidateDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @Operation(summary = "Delete candidate by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Candidate id") @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}