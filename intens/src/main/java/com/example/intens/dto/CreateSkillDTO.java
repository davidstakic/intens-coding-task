package com.example.intens.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSkillDTO {
	
	@NotBlank(message = "Skill name is required.")
	private String name;
	
}
