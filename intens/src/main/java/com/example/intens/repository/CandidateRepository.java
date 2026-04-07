package com.example.intens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.intens.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

	@Query("SELECT c FROM Candidate c LEFT JOIN FETCH c.skills WHERE c.id = :id")
    Optional<Candidate> findByIdWithSkills(@Param("id") Long id);
	
}
