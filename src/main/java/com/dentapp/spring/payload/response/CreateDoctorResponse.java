package com.dentapp.spring.payload.response;

import lombok.Data;

@Data
public class CreateDoctorResponse {
    private Long id;
    private String doctorName;
}
