package com.dentapp.spring.service;

import com.dentapp.spring.models.Doctor;
import com.dentapp.spring.payload.request.CreateDoctorRequest;
import com.dentapp.spring.payload.request.UpdateDoctorRequest;
import com.dentapp.spring.payload.response.CreateDoctorResponse;
import com.dentapp.spring.payload.response.SuccessResponse;
import com.dentapp.spring.payload.response.UpdateDoctorResponse;

import java.util.List;

public interface DoctorService {
    CreateDoctorResponse createDoctor(CreateDoctorRequest request);
    UpdateDoctorResponse updateDoctor(UpdateDoctorRequest request);
    SuccessResponse deleteDoctorById(Long id);
    List<Doctor> getAll();
    List<Doctor> getById(String id);
}
