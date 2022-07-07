package com.dentapp.spring.repository;

import com.dentapp.spring.models.PatientTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientTreatmentRepository extends JpaRepository<PatientTreatment, Long> {
}
