package com.dentapp.spring.payload.response.Auth;

import com.dentapp.spring.models.Auth.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class ValidateTokenResponse implements Serializable {
    private User user;
}
