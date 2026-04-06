package com.example.intens.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
