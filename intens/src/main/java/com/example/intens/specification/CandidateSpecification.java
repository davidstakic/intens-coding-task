package com.example.intens.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.intens.model.Candidate;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Expression;

public class CandidateSpecification {
	
	public static Specification<Candidate> hasName(String name) {
	    return (root, query, criteriaBuilder) -> {
	        if (name == null || name.isBlank()) {
	            return criteriaBuilder.conjunction();
	        }
	        
	        String pattern = "%" + name.toLowerCase().trim() + "%";
	        
	        Expression<String> fullName = criteriaBuilder.lower(
	            criteriaBuilder.concat(
	                criteriaBuilder.concat(root.get("firstName"), " "),
	                root.get("lastName")
	            )
	        );
	        
	        Expression<String> fullNameReversed = criteriaBuilder.lower(
	            criteriaBuilder.concat(
	                criteriaBuilder.concat(root.get("lastName"), " "),
	                root.get("firstName")
	            )
	        );
	        
	        return criteriaBuilder.or(
	            criteriaBuilder.like(fullName, pattern),
	            criteriaBuilder.like(fullNameReversed, pattern),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), pattern),
	            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), pattern)
	        );
	    };
	}

    public static Specification<Candidate> hasSkill(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            Join<Object, Object> skillJoin = root.join("skills", JoinType.INNER);

            return criteriaBuilder.like(
                criteriaBuilder.lower(skillJoin.get("name")),
                "%" + name.toLowerCase() + "%"
            );
        };
    }
}
