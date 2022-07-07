package com.dentapp.spring.service;

import com.dentapp.spring.models.Issue;
import com.dentapp.spring.models.IssueType;
import com.dentapp.spring.payload.request.CreateIssueRequest;
import com.dentapp.spring.payload.request.GetIssueRequest;
import com.dentapp.spring.payload.request.UpdateIssueRequest;
import com.dentapp.spring.payload.response.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IssueService {
    CreateIssueResponse createIssue(CreateIssueRequest request);
    UpdateIssueResponse updateIssue(UpdateIssueRequest request);
    GetAllIssueResponse getAllIssue();
    GetIssueResponse getIssueById(GetIssueRequest request);
    SuccessResponse deleteIssueById(Long id);
    ResponseEntity<List<IssueType>> getIssueTypes();
    ResponseEntity<?> setIssueType(String issueType);
    GetAllIssueResponse getIssueByType(String type);
    GetAllIssueResponse getAllByPatientId(String fullName);
}
