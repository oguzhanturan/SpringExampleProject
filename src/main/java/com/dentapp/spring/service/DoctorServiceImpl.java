package com.dentapp.spring.service;

import com.dentapp.spring.models.Auth.ERole;
import com.dentapp.spring.models.Auth.Role;
import com.dentapp.spring.models.Auth.User;
import com.dentapp.spring.models.Doctor;
import com.dentapp.spring.payload.request.CreateDoctorRequest;
import com.dentapp.spring.payload.request.UpdateDoctorRequest;
import com.dentapp.spring.payload.response.CreateDoctorResponse;
import com.dentapp.spring.payload.response.SuccessResponse;
import com.dentapp.spring.payload.response.UpdateDoctorResponse;
import com.dentapp.spring.repository.Auth.RoleRepository;
import com.dentapp.spring.repository.Auth.UserRepository;
import com.dentapp.spring.repository.DoctorRepository;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final ModelMapper mapper;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DoctorServiceImpl(ModelMapper mapper, DoctorRepository doctorRepository, UserRepository userRepository,RoleRepository roleRepository) {
        this.mapper = mapper;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public CreateDoctorResponse createDoctor(CreateDoctorRequest request) {
        if (!doctorRepository.existsByName(request.getDoctorName())) {
            Doctor map = Doctor.builder().name(request.getDoctorName()).status(true).isUser(false).build();
            Doctor doctor = doctorRepository.saveAndFlush(map);
            return mapper.map(doctor, CreateDoctorResponse.class);
        } else {
            Date date = new Date();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Doctor name already exists!");
        }

    }

    @Override
    public UpdateDoctorResponse updateDoctor(UpdateDoctorRequest request) {
        Optional<Doctor> doctor = doctorRepository.findById(request.getId());

        if (doctor.isPresent()) {
            if (!doctor.get().getName().equals(request.getDoctorName())) {
                if (doctorRepository.existsByName(request.getDoctorName())) {
                    throw new RuntimeException("Error: Exist Issue Type");
                }
            }
            doctor.get().setName(request.getDoctorName());
            return mapper.map(doctorRepository.save(doctor.get()), UpdateDoctorResponse.class);
        } else {
            throw new RuntimeException("Error:Issue Type is not found.");
        }
    }

    @Override
    public SuccessResponse deleteDoctorById(Long id) {
        var delete = doctorRepository.findById(id);
        delete.get().setStatus(false);
        doctorRepository.save(delete.get());
        return new SuccessResponse("success");
    }

    @Override
    public List<Doctor> getAll() {
        final List<User> byRoles_ = userRepository.findByRoles_(roleRepository.findByName(ERole.ROLE_MODERATOR).get());
        List<Doctor> doctors = new ArrayList<>();

        byRoles_.forEach(user -> {
            Doctor temp = new Doctor();
            temp.setName(user.getFullname());
            doctors.add(temp);
        });

        doctors.addAll(doctorRepository.findAll());
        return doctors;
    }

    @Override
    public List<Doctor> getById(String id) {
        return List.of(doctorRepository.findById(Long.parseLong(id)).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor not found");
        }));
    }
}
