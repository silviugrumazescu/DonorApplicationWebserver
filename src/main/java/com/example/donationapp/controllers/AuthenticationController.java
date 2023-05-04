package com.example.donationapp.controllers;

import com.example.donationapp.dto.LoginRequest;
import com.example.donationapp.dto.LoginResponse;
import com.example.donationapp.dto.RegisterRequest;
import com.example.donationapp.model.BloodType;
import com.example.donationapp.model.Donor;
import com.example.donationapp.model.Role;
import com.example.donationapp.model.User;
import com.example.donationapp.repository.UserRepository;
import com.example.donationapp.utils.DataParser;
import com.example.donationapp.utils.JwtUtils;
import com.example.donationapp.security.UserDetailsImpl;
import com.example.donationapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    DataParser dataParser;

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginResponse(
                userDetails.getEmail(),
                userDetails.getName(),
                jwt,
                userDetails.getDistrict().name(),
                userDetails.getCNP(),
                userDetails.getRole()
        ));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        String encodedPassword =  encoder.encode(registerRequest.getPassword());

        Donor user;
        try {
            user = new Donor(
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    encodedPassword,
                    dataParser.parseDistrict(registerRequest.getDistrict()),
                    Role.Donor,
                    dataParser.parseBloodType(registerRequest.getBloodType()),
                    registerRequest.getCNP(),
                    registerRequest.getPhoneNumber());

        } catch(DataParser.InvalidInputException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        try {
            authService.registerUser(user);
        } catch (AuthService.UserAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok().body("User registered successfully!");
    }
}
