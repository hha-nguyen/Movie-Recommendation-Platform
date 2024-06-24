package com.mesto.movieplatform.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mesto.movieplatform.dtoes.AuthResponseDTO;
import com.mesto.movieplatform.dtoes.LoginDTO;
import com.mesto.movieplatform.dtoes.RegisterDTO;
import com.mesto.movieplatform.dtoes.UserDTO;
import com.mesto.movieplatform.entities.Role;
import com.mesto.movieplatform.entities.User;
import com.mesto.movieplatform.repository.RoleRepository;
import com.mesto.movieplatform.repository.UserRepository;
import com.mesto.movieplatform.security.JWTGenerator;
import com.mesto.movieplatform.services.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(UserRepository userRepository, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleRepository.findByName("USER");
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", org.springframework.http.HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        // add user details to the response
        User user = userRepository.findUserByEmail(loginDTO.getEmail()).get();

        return new ResponseEntity<>(new AuthResponseDTO(token, user.getId()), org.springframework.http.HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("User logged out successfully!", org.springframework.http.HttpStatus.OK);
    }

}
