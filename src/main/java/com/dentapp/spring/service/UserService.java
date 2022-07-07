package com.dentapp.spring.service;

import com.dentapp.spring.payload.response.DeleteTechnicianByIdResponse;
import com.dentapp.spring.payload.response.GetAllTechnicianResponse;
import com.dentapp.spring.payload.response.GetAllUserResponse;
import com.dentapp.spring.payload.response.GetTechnicianByIdResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    GetAllUserResponse getAllUser();

    GetAllTechnicianResponse getAllTechnician();

    GetTechnicianByIdResponse getTechnicianById(long id);

    DeleteTechnicianByIdResponse deleteUser(long id);
}
