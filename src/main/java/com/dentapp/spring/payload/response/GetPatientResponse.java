package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPatientResponse implements Serializable {
    private Patient patient;
}
