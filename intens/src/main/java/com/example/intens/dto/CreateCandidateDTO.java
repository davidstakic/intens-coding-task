package com.example.intens.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCandidateDTO {
	
	@NotBlank(message = "Candidate's first name is required.")
    private String firstName;

    @NotBlank(message = "Candidate's last name is required.")
    private String lastName;

    @NotNull(message = "Candidate's birth date is required.")
    @Past(message = "Candidate's birth date must be in the past.")
    private LocalDate birthDate;

    @NotBlank(message = "Candidate's phone number is required.")
    @Pattern(regexp = "^06\\d{7,8}$", message = "Candidate's phone number must start with 06 and be followed by 7 or 8 digits.")
    private String phoneNumber;

    @Email(message = "Candidate's email should be valid.")
    @NotBlank(message = "Candidate's email is required.")
    private String email;

    @NotEmpty(message = "At least one skill must be selected.")
    private Set<Long> skillIds;
    
}
