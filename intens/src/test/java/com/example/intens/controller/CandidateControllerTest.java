package com.example.intens.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.intens.dto.CreateCandidateDTO;
import com.example.intens.dto.CandidateDTO;
import com.example.intens.dto.UpdateCandidateDTO;
import com.example.intens.exception.GlobalExceptionHandler;
import com.example.intens.service.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(CandidateController.class)
@Import(GlobalExceptionHandler.class)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService service;

    @Autowired
    private ObjectMapper objectMapper;
    

    @Test
    @DisplayName("POST /api/candidates - should create candidate when DTO is valid")
    public void shouldCreateCandidateWithValidDto() throws Exception {
        CreateCandidateDTO dto = CreateCandidateDTO.builder()
                .firstName("Kevin")
                .lastName("De Bruyne")
                .birthDate(LocalDate.of(1991, 6, 28))
                .phoneNumber("0612345678")
                .email("debruyne@gmail.com")
                .skillIds(Set.of(1L))
                .build();

        CandidateDTO saved = CandidateDTO.builder()
                .id(1L)
                .firstName("Kevin")
                .lastName("De Bruyne")
                .birthDate(LocalDate.of(1991, 6, 28))
                .phoneNumber("0612345678")
                .email("debruyne@gmail.com")
                .build();

        when(service.create(any())).thenReturn(saved);

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("debruyne@gmail.com"));

        verify(service).create(any());
    }

    @Test
    @DisplayName("POST /api/candidates - should not create candidate when DTO is invalid")
    public void shouldNotCreateCandidateWithInvalidDto() throws Exception {
        CreateCandidateDTO dto = CreateCandidateDTO.builder()
                .firstName("")
                .lastName("")
                .email("invalid")
                .build();

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    @Test
    @DisplayName("POST /api/candidates - should not create candidate when email exists")
    public void shouldNotCreateCandidateWithExistingEmail() throws Exception {
        CreateCandidateDTO dto = CreateCandidateDTO.builder()
                .firstName("Harry")
                .lastName("Kane")
                .birthDate(LocalDate.of(1993, 7, 28))
                .phoneNumber("0612345678")
                .email("kane@gmail.com")
                .skillIds(Set.of(1L))
                .build();

        when(service.create(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/candidates/{id} - should return candidate when id is valid")
    public void shouldGetCandidateByIdWithValidId() throws Exception {
        CandidateDTO dto = CandidateDTO.builder()
                .id(1L)
                .firstName("Marco")
                .lastName("Reus")
                .email("reus@gmail.com")
                .build();

        when(service.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/candidates/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("reus@gmail.com"));

        verify(service).getById(1L);
    }

    @Test
    @DisplayName("GET /api/candidates/{id} - should not return candidate when id is invalid")
    public void shouldNotGetCandidateByIdWithInvalidId() throws Exception {
        when(service.getById(99L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/candidates/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/candidates/search - should search candidates with pagination")
    public void shouldSearchCandidates() throws Exception {
        CandidateDTO candidate = CandidateDTO.builder()
                .id(1L)
                .firstName("David")
                .lastName("Silva")
                .birthDate(LocalDate.of(1986, 1, 8))
                .phoneNumber("0612345678")
                .email("silva@gmail.com")
                .skills(Set.of())
                .build();

        Page<CandidateDTO> page = new PageImpl<>(List.of(candidate), PageRequest.of(0, 10), 1);

        when(service.search("David", "", 0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/candidates/search")
                        .param("name", "David")
                        .param("skill", "")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("silva@gmail.com"));

        verify(service).search("David", "", 0, 10);
    }

    @Test
    @DisplayName("PUT /api/candidates - should update candidate when DTO is valid")
    public void shouldUpdateCandidateWithValidDto() throws Exception {
        UpdateCandidateDTO dto = UpdateCandidateDTO.builder()
                .id(1L)
                .firstName("Ousmane Updated")
                .lastName("Dembele")
                .birthDate(LocalDate.of(1997, 5, 15))
                .phoneNumber("0612345678")
                .email("dembele.updated@gmail.com")
                .skillIds(Set.of(1L))
                .build();

        CandidateDTO updated = CandidateDTO.builder()
                .id(1L)
                .firstName("Ousmane Updated")
                .email("dembele.updated@gmail.com")
                .build();

        when(service.update(any())).thenReturn(updated);

        mockMvc.perform(put("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ousmane Updated"));

        verify(service).update(any());
    }

    @Test
    @DisplayName("PUT /api/candidates - should not update candidate when DTO is invalid")
    public void shouldNotUpdateCandidateWithInvalidDto() throws Exception {
        UpdateCandidateDTO dto = UpdateCandidateDTO.builder()
                .id(1L)
                .firstName("")
                .email("invalid")
                .build();

        mockMvc.perform(put("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(service, never()).update(any());
    }

    @Test
    @DisplayName("PUT /api/candidates - should not update candidate when id is invalid")
    public void shouldNotUpdateCandidateWithInvalidId() throws Exception {
        UpdateCandidateDTO dto = UpdateCandidateDTO.builder()
                .id(99L)
                .firstName("Goran")
                .lastName("Pandev")
                .birthDate(LocalDate.of(1983, 7, 27))
                .phoneNumber("0612345678")
                .email("pandev@gmail.com")
                .skillIds(Set.of(1L))
                .build();

        when(service.update(any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/candidates/{id} - should delete candidate when id is valid")
    public void shouldDeleteCandidateWithValidId() throws Exception {
        mockMvc.perform(delete("/api/candidates/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/candidates/{id} - should not delete candidate when id is invalid")
    public void shouldNotDeleteCandidateWithInvalidId() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).delete(99L);

        mockMvc.perform(delete("/api/candidates/99"))
                .andExpect(status().isNotFound());
    }
    
}
