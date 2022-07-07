package com.dentapp.spring.repository;

import com.dentapp.spring.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByName(String name);
}
