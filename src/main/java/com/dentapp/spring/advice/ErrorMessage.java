package com.dentapp.spring.advice;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorMessage {
  private final int statusCode;
  private final Date timestamp;
  private final String message;
  private final String description;

  public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }
}