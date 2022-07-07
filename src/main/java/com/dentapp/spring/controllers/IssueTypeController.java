package com.dentapp.spring.controllers;


import com.dentapp.spring.payload.request.CreateIssueTypeRequest;
import com.dentapp.spring.payload.request.UpdateIssueTypeRequest;
import com.dentapp.spring.payload.response.CreateIssueTypeResponse;
import com.dentapp.spring.payload.response.SuccessResponse;
import com.dentapp.spring.payload.response.UpdateIssueTypeResponse;
import com.dentapp.spring.service.IssueTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/issueType")
public class IssueTypeController {

    private final IssueTypeService issueTypeService;

    public IssueTypeController(final IssueTypeService issueTypeService) {
        this.issueTypeService = issueTypeService;
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-issueType")
    public ResponseEntity<CreateIssueTypeResponse>  createIssueTypeResponse(@RequestBody CreateIssueTypeRequest request) {
        return ResponseEntity.ok(issueTypeService.createIssueType(request));
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update-issueType")
    public ResponseEntity<UpdateIssueTypeResponse>  updateIssueTypeResponse(@RequestBody UpdateIssueTypeRequest request) {
        return ResponseEntity.ok(issueTypeService.updateIssueType(request));
    }

    
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public SuccessResponse deleteIssueTypeById(@PathVariable Long id) {
        return issueTypeService.deleteIssueTypeById(id);
    }

}
