package com.dentapp.spring.repository;

import com.dentapp.spring.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByFullName(String fullName);
    Patient findByPhoneNumber(String phoneNumber);
    boolean existsByFullName(String patientName);
}
