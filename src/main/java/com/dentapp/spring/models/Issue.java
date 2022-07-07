package com.dentapp.spring.models;

import com.dentapp.spring.models.Auth.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Issue {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    private String issueId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="statusType")
    private StatusType statusType;

    @OneToOne
    @JoinColumn(name = "issue_type_id")
    private IssueType issueType;

    private LocalDateTime startDate;

    private LocalDateTime possibleEndDate;

    private LocalDateTime endDate;

    private Boolean agreementClinic;

    private Boolean agreementTechnician;

    @OneToMany(targetEntity=Note.class)
    private List clinicNote;

    @OneToMany(targetEntity=Note.class)
    private List technicianNote;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDate createDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDate modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name="currentStatus")
    private StatusType currentStatus;
}
