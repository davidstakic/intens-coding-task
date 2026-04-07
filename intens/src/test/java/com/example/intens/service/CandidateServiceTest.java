package com.example.intens.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.intens.dto.CandidateDTO;
import com.example.intens.dto.CreateCandidateDTO;
import com.example.intens.dto.UpdateCandidateDTO;
import com.example.intens.model.Candidate;
import com.example.intens.model.Skill;
import com.example.intens.repository.CandidateRepository;
import com.example.intens.repository.SkillRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

	@InjectMocks
	private CandidateService service;
	
	@Mock
	private CandidateRepository candidateRepository;
	
	@Mock
	private SkillRepository skillRepository;
	
	
	@Test
    @DisplayName("Should create candidate when DTO is valid")
    public void shouldCreateCandidateWithValidDto() {
        Skill skill = Skill.builder().id(1L).name("C++").build();
        when(skillRepository.findAllById(Set.of(1L))).thenReturn(List.of(skill));
        when(candidateRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateCandidateDTO dto = CreateCandidateDTO.builder()
                .firstName("Bruno")
                .lastName("Fernandes")
                .birthDate(LocalDate.of(1994, 9, 8))
                .phoneNumber("061322454")
                .email("fernandes@gmail.com")
                .skillIds(Set.of(1L))
                .build();

        CandidateDTO result = service.create(dto);

        assertEquals("Bruno", result.getFirstName());
        assertEquals(1, result.getSkills().size());
        assertEquals("C++", result.getSkills().iterator().next().getName());
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    @DisplayName("Should get candidate when id is valid")
    public void shouldGetCandidateByIdWithValidId() {
        Candidate candidate = Candidate.builder()
                .id(1L)
                .firstName("Zinedine")
                .lastName("Zidane")
                .skills(Set.of())
                .build();

        when(candidateRepository.findByIdWithSkills(1L)).thenReturn(Optional.of(candidate));

        CandidateDTO dto = service.getById(1L);

        assertEquals("Zinedine", dto.getFirstName());
        assertEquals(1L, dto.getId());
    }

    @Test
    @DisplayName("Should throw exception when id is invalid")
    public void shouldNotGetCandidateByIdWithInvalidId() {
        when(candidateRepository.findByIdWithSkills(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(99L));
    }
    
    @Test
    @DisplayName("Should search candidates with pagination")
    public void shouldSearchCandidates() {
        Candidate candidate = Candidate.builder()
                .id(1L)
                .firstName("Romelu")
                .lastName("Lukaku")
                .email("lukaku@gmail.com")
                .skills(Set.of())
                .build();

        Page<Candidate> page = new PageImpl<>(List.of(candidate));

        when(candidateRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<CandidateDTO> result = service.search("David", "", 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Romelu", result.getContent().get(0).getFirstName());

        verify(candidateRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Should update candidate when DTO is valid")
    public void shouldUpdateCandidateWithValidDto() {
        Candidate existing = Candidate.builder()
                .id(1L)
                .firstName("Diego")
                .lastName("Maradona")
                .skills(Set.of())
                .build();

        Skill skill = Skill.builder().id(1L).name("Vue").build();

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(skillRepository.findAllById(Set.of(1L))).thenReturn(List.of(skill));
        when(candidateRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UpdateCandidateDTO dto = UpdateCandidateDTO.builder()
                .id(1L)
                .firstName("Diego Updated")
                .lastName("Maradona Updated")
                .birthDate(LocalDate.of(1960, 10, 30))
                .phoneNumber("061659832")
                .email("maradona.updated@gmail.com")
                .skillIds(Set.of(1L))
                .build();

        CandidateDTO result = service.update(dto);

        assertEquals("Diego Updated", result.getFirstName());
        assertEquals(1, result.getSkills().size());
        assertEquals("Vue", result.getSkills().iterator().next().getName());
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    @DisplayName("Should throw exception when updating candidate with invalid id")
    public void shouldNotUpdateCandidateWithInvalidId() {
        UpdateCandidateDTO dto = UpdateCandidateDTO.builder()
                .id(99L)
                .firstName("Robert")
                .lastName("Lewandowski")
                .skillIds(Set.of())
                .build();

        when(candidateRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(dto));
    }

    @Test
    @DisplayName("Should delete candidate when id is valid")
    public void shouldDeleteCandidateWithValidId() {
        Candidate candidate = Candidate.builder().id(1L).build();
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));

        service.delete(1L);

        verify(candidateRepository).delete(any(Candidate.class));
    }

    @Test
    @DisplayName("Should throw exception when deleting candidate with invalid id")
    public void shouldNotDeleteCandidateWithInvalidId() {
        when(candidateRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(99L));
    }

}
