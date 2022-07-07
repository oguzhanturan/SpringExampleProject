package com.dentapp.spring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PatientTreatment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDateTime appointmentDate;

	@Column
	private LocalDateTime endTreatmentDate;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private LocalDate createDate;

	@Column(name = "modified_date")
	@LastModifiedDate
	private LocalDate modifiedDate;
}
