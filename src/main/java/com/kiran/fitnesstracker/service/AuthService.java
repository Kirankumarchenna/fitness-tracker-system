package com.kiran.fitnesstracker.service;

import com.kiran.fitnesstracker.dto.AuthResponseDTO;
import com.kiran.fitnesstracker.dto.LoginRequestDTO;
import com.kiran.fitnesstracker.dto.RegisterRequestDTO;
import com.kiran.fitnesstracker.dto.UserDTO;
import com.kiran.fitnesstracker.entity.User;
import com.kiran.fitnesstracker.exception.BadRequestException;
import com.kiran.fitnesstracker.exception.ResourceNotFoundException;
import com.kiran.fitnesstracker.repository.UserRepository;
import com.kiran.fitnesstracker.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
    Service for authentication and authorization
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //Register a new user
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        log.info("Registering new user with email: {}", registerRequest.getEmail());

        //check if user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email already registered: " + registerRequest.getEmail());
        }

        //validate role
        User.Role role;
        try {
            role = User.Role.valueOf(registerRequest.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + registerRequest.getRole());
        }

        //create and save user
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        //Generate token
        String token = jwtUtil.generateToken(
                savedUser.getEmail(),
                savedUser.getId(),
                savedUser.getRole().toString()
        );

        return AuthResponseDTO.builder()
                .token(token)
                .user(UserDTO.fromEntity(savedUser))
                .message("User registered successfully")
                .build();
    }

    //Login user and return JWT Token
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Logging in user with email: {}", loginRequest.getEmail());

        try {
            //Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            //Generate token
            String token = jwtUtil.generateToken(authentication);

            //get user details
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", loginRequest.getEmail()));

            log.info("User logged in successfully: {}", loginRequest.getEmail());

            return AuthResponseDTO.builder()
                    .token(token)
                    .user(UserDTO.fromEntity(user))
                    .message("Login Successful")
                    .build();
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", loginRequest.getEmail());
            throw new BadRequestException("Invalid email or password");
        }
    }
}
