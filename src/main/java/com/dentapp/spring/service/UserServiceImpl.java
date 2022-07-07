package com.dentapp.spring.service;

import com.dentapp.spring.models.Auth.ERole;
import com.dentapp.spring.models.Auth.User;
import com.dentapp.spring.payload.response.DeleteTechnicianByIdResponse;
import com.dentapp.spring.payload.response.GetAllTechnicianResponse;
import com.dentapp.spring.payload.response.GetAllUserResponse;
import com.dentapp.spring.payload.response.GetTechnicianByIdResponse;
import com.dentapp.spring.repository.Auth.RoleRepository;
import com.dentapp.spring.repository.Auth.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public GetAllUserResponse getAllUser() {
        return new GetAllUserResponse(userRepository.findAll());
    }

    @Override
    public GetAllTechnicianResponse getAllTechnician() {
        return new GetAllTechnicianResponse(userRepository.findByRoles_(roleRepository.findByName(ERole.ROLE_USER).get()));
    }

    @Override
    public GetTechnicianByIdResponse getTechnicianById(long id) {
        GetTechnicianByIdResponse response = new GetTechnicianByIdResponse();
        ResponseEntity.ok(userRepository.findById(id));
        return response;
    }

    @Override
    public DeleteTechnicianByIdResponse deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id"));
        user.setEnabled(false);
        userRepository.save(user);
        return new DeleteTechnicianByIdResponse("ok");
    }
}
