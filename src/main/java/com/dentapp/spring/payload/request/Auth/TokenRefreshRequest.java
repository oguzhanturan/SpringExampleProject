package com.dentapp.spring.payload.request.Auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;
}
