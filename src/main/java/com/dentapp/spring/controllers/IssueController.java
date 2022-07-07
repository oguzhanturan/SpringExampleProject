package com.dentapp.spring.controllers;

import com.dentapp.spring.models.IssueType;
import com.dentapp.spring.payload.request.CreateIssueRequest;
import com.dentapp.spring.payload.request.GetIssueRequest;
import com.dentapp.spring.payload.request.UpdateIssueRequest;
import com.dentapp.spring.payload.response.*;
import com.dentapp.spring.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/issue")
public class IssueController {

	private final IssueService issueService;

	@Autowired
	public IssueController(final IssueService issueService) {
		this.issueService = issueService;
	}


	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/create-issue")
	public CreateIssueResponse createIssue(@RequestBody final CreateIssueRequest request) {
		return issueService.createIssue(request);
	}

	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/update-issue")
	public UpdateIssueResponse updateIssue(@RequestBody final UpdateIssueRequest request) {
		return issueService.updateIssue(request);
	}

	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/get-issue")
	public GetIssueResponse getIssueById(@RequestBody final GetIssueRequest request) {
		return issueService.getIssueById(request);
	}

	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get-all")
	public GetAllIssueResponse getAllIssue() {
		return issueService.getAllIssue();
	}

	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/{type}")
	public GetAllIssueResponse getIssueByType(@PathVariable String type) {
		return issueService.getIssueByType(type);
	}


	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get-issues")
	public ResponseEntity<List<IssueType>> getIssueTypes() {
		return issueService.getIssueTypes();
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/get-issues")
	public ResponseEntity<?> updateIssue(String issueType) {
		return issueService.setIssueType(issueType);
	}

	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping(value = "/delete/{id}")
	public SuccessResponse deleteIssueById(@PathVariable Long id) {
		return issueService.deleteIssueById(id);
	}

	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/get-all-byPatient/{fullName}")
	public GetAllIssueResponse getAllByPatientId(@PathVariable final String fullName) {
		return issueService.getAllByPatientId(fullName);
	}
}
