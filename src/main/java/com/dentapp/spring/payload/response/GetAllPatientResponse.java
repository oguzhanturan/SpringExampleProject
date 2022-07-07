package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPatientResponse implements Serializable {
    private List<Patient> patientList;
}
