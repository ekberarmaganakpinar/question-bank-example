/* (C)2024 */
package com.questionbank.questionbank.specification;

import org.springframework.data.jpa.domain.Specification;

import com.questionbank.questionbank.entity.Parent;
import com.questionbank.questionbank.entity.Student;

public class ParentSpecification {

    public static Specification<Parent> hasStudent(final Student student) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("student"), student));
    }
}
