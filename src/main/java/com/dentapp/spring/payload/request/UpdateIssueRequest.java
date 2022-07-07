package com.dentapp.spring.payload.request;

import com.dentapp.spring.models.PatientTreatment;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
public class UpdateIssueRequest {
    private String issueId;
    private String name; 
    private String status; 
    private String possibleEndDate; 
    private String endDate; 
    private Boolean agreementClinic;
    private String clinicNote;
    private PatientTreatmentRequest patientTreatment;
    private String technicianNote;
    private String technicianId;
    private Boolean treatmentCompleted;
    private String doctorId;
    private String currentStatus;
}
