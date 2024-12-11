package com.railwayBooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railwayBooking.config.JwtProvider;
import com.railwayBooking.model.User;
import com.railwayBooking.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User inputUser) {
        if(inputUser.getUsername()==null) return new ResponseEntity<>("Please give username ",HttpStatus.BAD_REQUEST);
        try {
            User user = userService.registerUser(inputUser.getEmail(), inputUser.getPassword(), inputUser.getRole(),inputUser.getUsername());
            return ResponseEntity.ok("User registered successfully: " + user.getEmail());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User inputUser) {
        User user = userService.findByEmail(inputUser.getEmail());
        if (user == null || !passwordEncoder.matches(inputUser.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        String token = JwtProvider.generateToken(new UsernamePasswordAuthenticationToken(inputUser.getEmail(), null, AuthorityUtils.createAuthorityList(user.getRole())));
        return ResponseEntity.ok(token);
    }
}
