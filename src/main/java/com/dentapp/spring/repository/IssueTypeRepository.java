package com.dentapp.spring.repository;

import com.dentapp.spring.models.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {
    Optional<IssueType> findByName(String name);
    boolean existsByName(String name);
    // TODO: issue find metodları yazılacak
}
