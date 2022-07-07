package com.dentapp.spring.payload.request;

import lombok.Data;

@Data
public class UpdateIssueTypeRequest {
    private Long id;
    private String name;
}
