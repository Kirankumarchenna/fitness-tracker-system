package com.kiran.fitnesstracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWorkoutPlanRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "The title should be in between 2 to 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description should be in between 10 to 1000 characters")
    private String description;

    @NotNull
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 365, message = "Duration cannot exceed 365 days")
    private Integer durationDays;

    @Pattern(regexp = "BEGINNER|INTERMEDIATE|ADVANCED", message = "Difficultly must be BEGINNER, INTERMEDIATE or ADVANCED")
    private String difficulty;
}
