package com.dentapp.spring.controllers;


import com.dentapp.spring.models.Doctor;
import com.dentapp.spring.payload.request.CreateDoctorRequest;
import com.dentapp.spring.payload.request.UpdateDoctorRequest;
import com.dentapp.spring.payload.response.CreateDoctorResponse;
import com.dentapp.spring.payload.response.SuccessResponse;
import com.dentapp.spring.payload.response.UpdateDoctorResponse;
import com.dentapp.spring.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(final DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<CreateDoctorResponse> createDoctorResponse(@RequestBody CreateDoctorRequest request) {
        return ResponseEntity.ok(doctorService.createDoctor(request));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @RequestMapping(value = {"/", "/{id}"})
    public ResponseEntity<?> getAllorById(@PathVariable(required = false) String id) {
        ResponseEntity<List<Doctor>> ok;
        if (id == null) {
            ok = ResponseEntity.ok(doctorService.getAll());
        } else {
            ok = ResponseEntity.ok(doctorService.getById(id));
        }
        return ok;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateDoctorResponse> updateDoctorResponse(@PathVariable String id, @RequestBody UpdateDoctorRequest request) {
        request.setId(Long.parseLong(id));
        return ResponseEntity.ok(doctorService.updateDoctor(request));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public SuccessResponse deleteDoctorById(@PathVariable Long id) {
        return doctorService.deleteDoctorById(id);
    }

}
