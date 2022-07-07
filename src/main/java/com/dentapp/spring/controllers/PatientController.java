package com.dentapp.spring.controllers;

import com.dentapp.spring.payload.request.GetAllPatientRequest;
import com.dentapp.spring.payload.request.GetPatientRequest;
import com.dentapp.spring.payload.response.GetAllPatientResponse;
import com.dentapp.spring.payload.response.GetPatientResponse;
import com.dentapp.spring.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/patient")
public class PatientController {

	@Autowired
	PatientService patientService;

	@GetMapping("/all-patients")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public GetAllPatientResponse getAllPatients() {
		return patientService.getAllPatients();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public GetPatientResponse getPatientById(@RequestBody final String id) {
		return patientService.getPatientById(id);
	}

	@PostMapping("/get-patient")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public GetPatientResponse getPatient(@RequestBody final GetPatientRequest request) {
		return patientService.getPatient(request);
	}
}
