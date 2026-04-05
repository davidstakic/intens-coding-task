package com.example.intens.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.intens.dto.CreateSkillDTO;
import com.example.intens.dto.SkillDTO;
import com.example.intens.dto.UpdateSkillDTO;
import com.example.intens.model.Skill;
import com.example.intens.repository.SkillRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SkillService {
	
	@Autowired
	private SkillRepository repository;
	

	public SkillDTO create(CreateSkillDTO dto) {
		Skill skill = Skill.builder()
				.name(dto.getName())
				.build();
		
		repository.save(skill);
		
		return SkillDTO.builder()
				.name(dto.getName())
				.build();
	}
	
	public SkillDTO getById(Long id) {
		Skill skill = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Skill not found with ID " + id));
		
		return SkillDTO.builder()
				.name(skill.getName())
				.build();
	}
	
	public Page<SkillDTO> searchByName(String name, int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Skill> skills = repository.findByNameContainingIgnoreCase(name, pageable);

	    return skills.map(skill -> SkillDTO.builder()
	                                      .id(skill.getId())
	                                      .name(skill.getName())
	                                      .build());
	}
	
	public SkillDTO update(UpdateSkillDTO dto) {
		Skill skill = repository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("Skill not found with ID " + dto.getId()));
		
		skill.setName(dto.getName());
		repository.save(skill);
		
		return SkillDTO.builder()
				.name(dto.getName())
				.build();
		
	}
	
	public void delete(Long id) {
		Skill skill = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Skill not found with ID " + id));
		
		repository.delete(skill);
	}
	
}
