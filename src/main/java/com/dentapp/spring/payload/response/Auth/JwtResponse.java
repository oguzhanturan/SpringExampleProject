package com.dentapp.spring.payload.response.Auth;

import com.dentapp.spring.models.Auth.Role;

import java.util.List;
import java.util.Set;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;
	private String fullname;
	private String email;
	private final Set<Role> roles;

	public JwtResponse(String accessToken, String refreshToken, Long id, String username,String fullname, String email, Set<Role>  roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {return fullname;}

	public void setFullname(String fullname) {this.fullname = fullname;}

	public Set<Role>  getRoles() {
		return roles;
	}

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
