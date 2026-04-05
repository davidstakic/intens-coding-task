package com.example.intens.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.intens.dto.CandidateDTO;
import com.example.intens.dto.CreateCandidateDTO;
import com.example.intens.dto.SkillDTO;
import com.example.intens.dto.UpdateCandidateDTO;
import com.example.intens.model.Candidate;
import com.example.intens.model.Skill;
import com.example.intens.repository.CandidateRepository;
import com.example.intens.repository.SkillRepository;
import com.example.intens.specification.CandidateSpecification;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	
	public CandidateDTO create(CreateCandidateDTO dto) {
        Set<Skill> skills = skillRepository.findAllById(dto.getSkillIds())
                                           .stream()
                                           .collect(Collectors.toSet());

        Candidate candidate = Candidate.builder()
                                       .firstName(dto.getFirstName())
                                       .lastName(dto.getLastName())
                                       .birthDate(dto.getBirthDate())
                                       .phoneNumber(dto.getPhoneNumber())
                                       .email(dto.getEmail())
                                       .skills(skills)
                                       .build();

        candidateRepository.save(candidate);

        return mapToDTO(candidate);
    }

    public CandidateDTO getById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID " + id));

        return mapToDTO(candidate);
    }
    
    public Page<CandidateDTO> search(String name, String skill, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Candidate> spec = Specification
                .where(CandidateSpecification.hasName(name))
                .and(CandidateSpecification.hasSkill(skill));

        Page<Candidate> candidates = candidateRepository.findAll(spec, pageable);

        return candidates.map(this::mapToDTO);
    }

    public CandidateDTO update(UpdateCandidateDTO dto) {
        Candidate candidate = candidateRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID " + dto.getId()));
        
        Set<Skill> skills = skillRepository.findAllById(dto.getSkillIds())
                .stream()
                .collect(Collectors.toSet());

        candidate.setFirstName(dto.getFirstName());
        candidate.setLastName(dto.getLastName());
        candidate.setBirthDate(dto.getBirthDate());
        candidate.setPhoneNumber(dto.getPhoneNumber());
        candidate.setEmail(dto.getEmail());
        candidate.setSkills(skills);

        candidateRepository.save(candidate);

        return mapToDTO(candidate);
    }

    public void delete(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID " + id));

        candidateRepository.delete(candidate);
    }

    private CandidateDTO mapToDTO(Candidate candidate) {
        Set<SkillDTO> skillDTOs = candidate.getSkills()
                                           .stream()
                                           .map(skill -> SkillDTO.builder()
                                                                 .id(skill.getId())
                                                                 .name(skill.getName())
                                                                 .build())
                                           .collect(Collectors.toSet());

        return CandidateDTO.builder()
                           .id(candidate.getId())
                           .firstName(candidate.getFirstName())
                           .lastName(candidate.getLastName())
                           .birthDate(candidate.getBirthDate())
                           .phoneNumber(candidate.getPhoneNumber())
                           .email(candidate.getEmail())
                           .skills(skillDTOs)
                           .build();
    }

}
