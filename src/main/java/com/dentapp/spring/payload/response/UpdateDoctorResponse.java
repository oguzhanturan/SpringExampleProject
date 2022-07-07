package com.dentapp.spring.payload.response;

import lombok.Data;

@Data
public class UpdateDoctorResponse {
    private Long id;
    private String doctorName;
}
