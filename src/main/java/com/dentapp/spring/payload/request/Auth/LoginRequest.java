package com.dentapp.spring.payload.request.Auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private String deviceId;
}
