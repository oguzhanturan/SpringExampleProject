package com.dentapp.spring.payload.request;

import lombok.Data;

@Data
public class PatientTreatmentRequest {
    private String id;

    private String appointmentDate;

    private String endTreatmentDate;
}
