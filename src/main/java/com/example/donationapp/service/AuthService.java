package com.example.donationapp.service;

import com.example.donationapp.model.Donor;
import com.example.donationapp.model.User;
import com.example.donationapp.repository.DonorRepository;
import com.example.donationapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonorRepository donorRepository;

    public void registerUser(Donor user) throws UserAlreadyExistsException{

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use!");
        }

        donorRepository.save(user);
    }

    public void loginUser(String email, String password) throws IncorrectCredentialsException {
        User u = userRepository.findByEmail(email);
        if(u == null) {
            throw new IncorrectCredentialsException("Email doesnt exist");
        }
        else {
            if(!u.getPassword().equals(password)) {
                throw new IncorrectCredentialsException("Incorrect password");
            }
        }
    }


    public class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String errorMessage) {
            super(errorMessage);
        }
    }

    public class IncorrectCredentialsException extends Exception {
        public IncorrectCredentialsException(String errorMessage) {
            super(errorMessage);
        }
    }

}
