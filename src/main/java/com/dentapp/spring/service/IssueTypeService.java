package com.dentapp.spring.service;

import com.dentapp.spring.payload.request.CreateIssueTypeRequest;
import com.dentapp.spring.payload.request.UpdateIssueTypeRequest;
import com.dentapp.spring.payload.response.CreateIssueTypeResponse;
import com.dentapp.spring.payload.response.SuccessResponse;
import com.dentapp.spring.payload.response.UpdateIssueTypeResponse;

public interface IssueTypeService {
    CreateIssueTypeResponse createIssueType(CreateIssueTypeRequest request);
    UpdateIssueTypeResponse updateIssueType(UpdateIssueTypeRequest request);
    SuccessResponse deleteIssueTypeById(Long id);
}
