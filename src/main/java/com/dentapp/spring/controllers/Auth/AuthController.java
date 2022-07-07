package com.dentapp.spring.controllers.Auth;

import com.dentapp.spring.advice.ErrorMessage;
import com.dentapp.spring.exception.TokenRefreshException;
import com.dentapp.spring.models.Auth.ERole;
import com.dentapp.spring.models.Auth.RefreshToken;
import com.dentapp.spring.models.Auth.Role;
import com.dentapp.spring.models.Auth.User;
import com.dentapp.spring.models.Doctor;
import com.dentapp.spring.payload.request.Auth.*;
import com.dentapp.spring.payload.request.DeleteUserRequest;
import com.dentapp.spring.payload.request.UpdateUserRequest;
import com.dentapp.spring.payload.response.Auth.JwtResponse;
import com.dentapp.spring.payload.response.Auth.MessageResponse;
import com.dentapp.spring.payload.response.Auth.TokenRefreshResponse;
import com.dentapp.spring.repository.Auth.RoleRepository;
import com.dentapp.spring.repository.Auth.UserRepository;
import com.dentapp.spring.repository.DoctorRepository;
import com.dentapp.spring.security.jwt.JwtUtils;
import com.dentapp.spring.security.services.RefreshTokenService;
import com.dentapp.spring.security.services.UserDetailsImpl;
import com.dentapp.spring.security.services.UserDetailsServiceImpl;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ModelMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // TODO : email ile login yapılacak
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId()).orElseThrow(() ->
                new UsernameNotFoundException("User cannot found!"));
        user.setDeviceId(loginRequest.getDeviceId());
        userRepository.save(user);

        String jwt = jwtUtils.generateJwtToken(userDetails);

        Role userRole = roleRepository.findByName(ERole.valueOf(userDetails.getAuthorities().toArray()[0].toString()))
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getFullname(), userDetails.getEmail(), roles));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setFullname(signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setEnabled(true);
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User save = userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest userRequest) throws NotFoundException {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
        Role role = roleRepository.findByName(ERole.valueOf(userRequest.getRoleName())).orElseThrow(() -> new NotFoundException("Role not found!"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setUsername(userRequest.getUsername());
        user.setFullname(userRequest.getFullName());
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User update successfull"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest userRequest) throws NotFoundException {
        userRepository.deleteById(userRequest.getId());
        return ResponseEntity.ok(new MessageResponse("User update successfull"));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/validation")
    public ResponseEntity<?> validateToken(@Valid @RequestBody ValidateTokenRequest jwt) {
        //TODO user status kontrol edilcek
        if (jwt != null && jwtUtils.validateJwtToken(jwt.getJwt())) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt.getJwt());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            User user = mapper.map(userDetails, User.class);
            Role userRole = roleRepository.findByName(ERole.valueOf(userDetails.getAuthorities().toArray()[0].toString()))
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            assert jwt != null;
            return new ResponseEntity<ErrorMessage>(HttpStatus.UNAUTHORIZED);
        }
    }
}
