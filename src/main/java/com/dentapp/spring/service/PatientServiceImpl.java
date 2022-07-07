package com.dentapp.spring.service;

import com.dentapp.spring.payload.request.GetPatientRequest;
import com.dentapp.spring.payload.response.GetAllPatientResponse;
import com.dentapp.spring.payload.response.GetPatientResponse;
import com.dentapp.spring.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(final PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public GetAllPatientResponse getAllPatients() {
        return new GetAllPatientResponse(patientRepository.findAll());
    }

    @Override
    public GetPatientResponse getPatientById(String id) {
        return new GetPatientResponse(patientRepository.findById(Long.parseLong(id)).orElse(null));
    }

    @Override
    public GetPatientResponse getPatient(GetPatientRequest request) {
        GetPatientResponse response = new GetPatientResponse();
        if (!request.getFullName().isEmpty()) {
            response.setPatient(patientRepository.findByFullName(request.getFullName()).get());
        } else {
            response.setPatient(patientRepository.findByPhoneNumber(request.getPhoneNumber()));
        }
        return response;
    }
}
