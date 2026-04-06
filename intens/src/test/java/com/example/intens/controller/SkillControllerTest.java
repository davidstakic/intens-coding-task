package com.example.intens.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.example.intens.dto.SkillDTO;
import com.example.intens.dto.CreateSkillDTO;
import com.example.intens.dto.UpdateSkillDTO;
import com.example.intens.exception.GlobalExceptionHandler;
import com.example.intens.service.SkillService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@WebMvcTest(SkillController.class)
@Import(GlobalExceptionHandler.class)
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SkillService service;

    @Autowired
    private ObjectMapper objectMapper;
    

    @Test
    @DisplayName("POST /api/skills - should create skill when DTO is valid")
    public void shouldCreateSkillWithValidDto() throws Exception {
        CreateSkillDTO dto = CreateSkillDTO.builder().name("Italian").build();
        SkillDTO saved = SkillDTO.builder().id(1L).name("Italian").build();

        when(service.create(any())).thenReturn(saved);

        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Italian"));

        verify(service).create(any());
    }
    
    @Test
    @DisplayName("POST /api/skills - should not create skill when DTO is invalid")
    public void shouldNotCreateSkillWithInvalidDto() throws Exception {
        CreateSkillDTO dto = CreateSkillDTO.builder().name("").build();

        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }
    
    @Test
    @DisplayName("POST /api/skills - should not create skill when name exists")
    public void shouldNotCreateSkillWithExistingName() throws Exception {
        CreateSkillDTO dto = CreateSkillDTO.builder().name("Java").build();

        when(service.create(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/skills/{id} - should return skill when id is valid")
    public void shouldGetSkillByIdWithValidId() throws Exception {
        SkillDTO dto = SkillDTO.builder().id(1L).name("Java").build();
        when(service.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/skills/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Java"));

        verify(service).getById(1L);
    }
    
    @Test
    @DisplayName("GET /api/skills/{id} - should not return skill when id is invalid")
    public void shouldNotGetSkillByIdWithInvalidId() throws Exception {
        when(service.getById(99L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/skills/99"))
               .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("GET /api/skills/search - should search skills with pagination")
    public void shouldSearchSkills() throws Exception {
        SkillDTO skill = SkillDTO.builder().id(1L).name("Java").build();
        Page<SkillDTO> page = new PageImpl<>(List.of(skill), PageRequest.of(0,10), 1);

        when(service.search("Java", 0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/skills/search")
                        .param("name", "Java")
                        .param("page", "0")
                        .param("size", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].name").value("Java"));

        verify(service).search("Java", 0, 10);
    }

    @Test
    @DisplayName("PUT /api/skills - should update skill when DTO is valid")
    public void shouldUpdateSkillWithValidDto() throws Exception {
        UpdateSkillDTO dto = UpdateSkillDTO.builder().id(1L).name("Java Updated").build();
        SkillDTO updated = SkillDTO.builder().id(1L).name("Java Updated").build();

        when(service.update(any())).thenReturn(updated);

        mockMvc.perform(put("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Java Updated"));

        verify(service).update(any());
    }
    
    @Test
    @DisplayName("PUT /api/skills - should not update skill when DTO is invalid")
    public void shouldNotUpdateSkillWithInvalidDto() throws Exception {
        UpdateSkillDTO dto = UpdateSkillDTO.builder().id(1L).name("").build();

        mockMvc.perform(put("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());

        verify(service, never()).update(any());
    }
    
    @Test
    @DisplayName("PUT /api/skills - should not update skill when id is invalid")
    public void shouldNotUpdateSkillWithInvalidId() throws Exception {
        UpdateSkillDTO dto = UpdateSkillDTO.builder().id(99L).name("Rust").build();

        when(service.update(any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/skills/{id} - should delete skill when id is valid")
    public void shouldDeleteSkillWithValidId() throws Exception {
        mockMvc.perform(delete("/api/skills/1"))
               .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
    
    @Test
    @DisplayName("DELETE /api/skills/{id} - should not delete skill when id is invalid")
    public void shouldNotDeleteSkillWithInvalidId() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).delete(99L);

        mockMvc.perform(delete("/api/skills/99"))
               .andExpect(status().isNotFound());
    }
    
}