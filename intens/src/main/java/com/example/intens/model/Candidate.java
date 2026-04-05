package com.example.intens.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Data
@Builder
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(unique = true)
	private String email;
	
	@ManyToMany
	@JoinTable(
	    name = "candidate_skill",
	    joinColumns = @JoinColumn(name = "candidate_id"),
	    inverseJoinColumns = @JoinColumn(name = "skill_id")
	)
	private Set<Skill> skills;

}
