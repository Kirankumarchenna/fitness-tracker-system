package com.kiran.fitnesstracker.service;

import com.kiran.fitnesstracker.dto.UserDTO;
import com.kiran.fitnesstracker.entity.User;
import com.kiran.fitnesstracker.exception.ResourceNotFoundException;
import com.kiran.fitnesstracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    //get user profile by ID
    public UserDTO getUserProfile(Long userId) {
        log.debug("Fetching user profile with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return UserDTO.fromEntity(user);
    }

    //Get current authenticated user profile
    public UserDTO getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        log.debug("Fetching current user profile: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return UserDTO.fromEntity(user);
    }

    //Get all trainers
    public List<UserDTO> getAllTrainers() {
        log.debug("Fetching all trainers");

        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.TRAINER && user.getActive())
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //Get user by email
    public UserDTO getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        return UserDTO.fromEntity(user);
    }

    //Check if current user is trainer
    public boolean isCurrentUserTrainer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_TRAINER"));
    }

    //check if current user is admin
    public boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    //Get currently authenticated user ID
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

    }
}
