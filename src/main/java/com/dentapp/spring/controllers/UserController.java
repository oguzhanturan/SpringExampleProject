package com.dentapp.spring.controllers;

import com.dentapp.spring.payload.response.DeleteTechnicianByIdResponse;
import com.dentapp.spring.payload.response.GetAllTechnicianResponse;
import com.dentapp.spring.payload.response.GetAllUserResponse;
import com.dentapp.spring.payload.response.GetTechnicianByIdResponse;
import com.dentapp.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@CrossOrigin(origins = "http://192.168.1.48:3000")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/users")
	public GetAllUserResponse getAllUser() {
		return userService.getAllUser();
	}

	@CrossOrigin(origins = "http://192.168.1.48:3000")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/technicians")
	public GetAllTechnicianResponse getAllTechnician() {
		return userService.getAllTechnician();
	}

	@CrossOrigin(origins = "http://192.168.1.48:3000")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/technicians/{id}")
	public GetTechnicianByIdResponse getTechnicianById(@PathVariable Long id) {
		return userService.getTechnicianById(id);
	}

	@CrossOrigin(origins = "http://192.168.1.48:3000")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping("/technicians/{id}")
	public DeleteTechnicianByIdResponse deleteUser(@PathVariable Long id) {
		return userService.deleteUser(id);
	}
}
