package com.dentapp.spring.payload.response;

import com.dentapp.spring.models.Auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class GetAllUserResponse implements Serializable {
    private List<User> userList;
}
