package com.kiran.fitnesstracker.controller;

import com.kiran.fitnesstracker.dto.AuthResponseDTO;
import com.kiran.fitnesstracker.dto.LoginRequestDTO;
import com.kiran.fitnesstracker.dto.RegisterRequestDTO;
import com.kiran.fitnesstracker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Authentication and authorization endpoints")
public class AuthController {

    private final AuthService authService;

    //Register new user
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account with USER, TRAINER, or ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input or email already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        log.info("Register endpoint called for email: {}", registerRequest.getEmail());
        AuthResponseDTO response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Login user
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and receive JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User login successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid email or password"),
            @ApiResponse(responseCode = "401", description = "Authentication failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Login endpoint called for email: {}", loginRequest.getEmail());
        AuthResponseDTO  response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}