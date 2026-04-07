package com.example.intens.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.intens.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>  {
	
	Page<Skill> findByNameContainingIgnoreCase(String name, Pageable pageable);


	@Modifying
	@Query(value = "DELETE FROM candidate_skill WHERE skill_id = :skillId", nativeQuery = true)
	void deleteSkillRelations(Long skillId);
	
}
