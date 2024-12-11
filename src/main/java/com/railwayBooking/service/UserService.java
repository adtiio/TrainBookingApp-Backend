package com.railwayBooking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.railwayBooking.model.User;
import com.railwayBooking.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String email, String password, String role,String username) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already registered.");
        }
        User user = new User(email, passwordEncoder.encode(password), role,username);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
