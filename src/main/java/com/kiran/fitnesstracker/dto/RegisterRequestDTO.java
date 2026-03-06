package com.kiran.fitnesstracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Email is required")
    @Size(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password should be required")
    @Size(min = 2, max = 20, message = "Password should contain 6 to 20 characters")
    private String password;

    @NotNull(message = "Role is required")
    private String role;
}
