package com.dentapp.spring.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CreateIssueRequest {
    private String patientName;
    private String userId;
    private String name;
    private String startDate;
    private String possibleEndDate;
    private String endDate;
    private Boolean agreementClinic;
    private Boolean agreementTechnician;
    private String clinicNote;
    private PatientTreatmentRequest patientTreatment;
    private String technicianNote;
    private String issueTypeId;
    private String doctor;
}
