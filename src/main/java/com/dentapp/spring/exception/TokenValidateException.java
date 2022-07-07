package com.dentapp.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenValidateException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TokenValidateException(String token, String message) {
    super(String.format("Failed for [%s]: %s", token, message));
  }
}
