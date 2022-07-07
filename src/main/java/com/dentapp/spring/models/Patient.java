package com.dentapp.spring.models;

import com.dentapp.spring.models.Auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(	name = "patient", uniqueConstraints = {
		@UniqueConstraint(columnNames = "fullName")
})
public class Patient {

	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	private Long id;

	@Size(max = 100)
	private String fullName;

	@Size(max = 11)
	private String phoneNumber;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(targetEntity=PatientTreatment.class)
	private List<PatientTreatment> patientTreatment;
}
