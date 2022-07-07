package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTechnicianByIdResponse implements Serializable {
    private User user;
}
