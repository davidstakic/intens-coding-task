package com.example.intens.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.intens.model.Candidate;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class CandidateSpecification {

	public static Specification<Candidate> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + name.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + name.toLowerCase() + "%")
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
