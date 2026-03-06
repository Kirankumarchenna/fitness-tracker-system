package com.kiran.fitnesstracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkoutLogRequestDTO {

    @NotNull(message = "Workout plan ID is required")
    private Long workoutPlanId;

    @NotNull(message = "Log date is required")
    @PastOrPresent(message = "Log date cannot be in future")
    private LocalDate logDate;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "PENDIND|COMPLETED|SKIPPED", message = "Status must be PENDING, COMPLETED or SKIPPED")
    private String status;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
