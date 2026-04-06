package com.example.intens.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.intens.dto.CreateSkillDTO;
import com.example.intens.dto.SkillDTO;
import com.example.intens.dto.UpdateSkillDTO;
import com.example.intens.model.Skill;
import com.example.intens.repository.SkillRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
	
	@InjectMocks
	private SkillService service;
	
	@Mock
	private SkillRepository repository;
	

	@Test
    @DisplayName("Should create skill when DTO is valid")
	public void shouldCreateSkillWithValidDto() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateSkillDTO dto = CreateSkillDTO.builder().name("New").build();
        SkillDTO saved = service.create(dto);

        assertEquals("New", saved.getName());
        verify(repository).save(any(Skill.class));
    }
	
	@Test
    @DisplayName("Should return skill when id is valid")
	public void shouldGetSkillByIdWithValidId() {
        Skill skill = Skill.builder().id(1L).name("Python").build();
        when(repository.findById(1L)).thenReturn(Optional.of(skill));

        SkillDTO dto = service.getById(1L);

        assertEquals("Python", dto.getName());
        assertEquals(1L, dto.getId());
    }
	
	@Test
    @DisplayName("Should throw exception when id is invalid")
	public void shouldNotGetSkillByIdWithInvalidId() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(99L));
    }
	
	@Test
    @DisplayName("Should update skill when DTO is valid")
	public void shouldUpdateSkillWithValidDto() {
        Skill existing = Skill.builder().id(1L).name("Old").build();
        UpdateSkillDTO dto = UpdateSkillDTO.builder().id(1L).name("New").build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        SkillDTO result = service.update(dto);

        assertEquals("New", result.getName());
        verify(repository).save(any());
    }
	
	@Test
    @DisplayName("Should throw exception when updating skill with invalid id")
	public void shouldNotUpdateSkillWithInvalidId() {
        UpdateSkillDTO dto = UpdateSkillDTO.builder().id(99L).name("New").build();
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(dto));
    }
	
	@Test
    @DisplayName("Should delete skill when id is valid")
	public void shouldDeleteSkillWithValidId() {
        Skill skill = Skill.builder().id(1L).name("To Delete").build();
        when(repository.findById(1L)).thenReturn(Optional.of(skill));

        service.delete(1L);

        verify(repository).delete(any());
    }

	
	@Test
    @DisplayName("Should throw exception when deleting skill with invalid id")
	public void shouldNotDeleteSkillWithInvalidId() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(99L));
    }

}
