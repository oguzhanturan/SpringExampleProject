package com.dentapp.spring.repository;

import com.dentapp.spring.models.Issue;
import com.dentapp.spring.models.IssueType;
import com.dentapp.spring.models.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    // TODO: issue find metodları yazılacak
    List<Issue> findByIssueType_(IssueType issueType);
    List<Issue> findByStatusType_(StatusType statusType);
    List<Issue> findByPossibleEndDateLessThanEqual(LocalDateTime possibleEndDate);
    List<Issue> findByPossibleEndDate(LocalDateTime now);
    List<Issue> findAllByPatientId_FullNameOrderByEndDateDesc(String fullName);
    List<Issue> findByCreateDate(LocalDate date);
}
