package com.dentapp.spring.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetAllPatientRequest {
    @NotBlank
    private Long tckNo;
}
