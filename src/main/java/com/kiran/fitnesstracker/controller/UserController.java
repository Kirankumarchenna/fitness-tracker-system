package com.kiran.fitnesstracker.controller;

import com.kiran.fitnesstracker.dto.UserDTO;
import com.kiran.fitnesstracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/*
    Rest controller for user management endpoint.
 */

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "User management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    //get current authenticated user profile
    @GetMapping("/me")
    @Operation(summary = "Get current user profile", description = "Retrieve the profile of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserDTO> getCurrentUserProfile(){
        log.info("Get current user profile endpoint called");
        UserDTO userDTO = userService.getCurrentUserProfile();
        return ResponseEntity.ok(userDTO);
    }

    //get user profile by ID
    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile by ID", description = "Retrieve a user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserDTO> getUserprofile(@PathVariable Long userId){
        log.info("Get user profile endpoint called for userId: {}", userId);
        UserDTO userDTO = userService.getUserProfile(userId);
        return ResponseEntity.ok(userDTO);
    }

    //get all trainers
    @GetMapping("/trainers/all")
    @Operation(summary = "Get all trainers", description = "Retrieve a list of all active trainers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainers list retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserDTO>> getAllTrainers(){
        log.info("Get all trainers endpoint called");
        List<UserDTO> trainers = userService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }
}
