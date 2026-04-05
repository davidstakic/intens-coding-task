package com.example.intens.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String phoneNumber;
	private String email;
	private Set<SkillDTO> skills;
}
