package com.dentapp.spring.payload.request;

import lombok.Data;

@Data
public class UpdateDoctorRequest {
    private Long id;
    private String doctorName;
}
