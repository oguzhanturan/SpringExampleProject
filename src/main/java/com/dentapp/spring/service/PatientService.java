package com.dentapp.spring.service;

import com.dentapp.spring.payload.request.GetAllPatientRequest;
import com.dentapp.spring.payload.request.GetPatientRequest;
import com.dentapp.spring.payload.response.GetAllPatientResponse;
import com.dentapp.spring.payload.response.GetPatientResponse;

public interface PatientService {

    GetAllPatientResponse getAllPatients();

    GetPatientResponse getPatientById(final String id);

    GetPatientResponse getPatient(final GetPatientRequest request);
}
