package com.example.intens.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSkillDTO {

    @NotNull(message = "Skill id is required.")
    private Long id;

    @NotBlank(message = "Skill name is required.")
    private String name;
}
