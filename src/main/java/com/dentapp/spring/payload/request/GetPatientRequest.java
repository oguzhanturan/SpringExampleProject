package com.dentapp.spring.payload.request;

import lombok.Data;

@Data
public class GetPatientRequest {

    private String fullName;
    private String phoneNumber;
}
