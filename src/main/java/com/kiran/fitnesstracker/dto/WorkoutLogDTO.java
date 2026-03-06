package com.kiran.fitnesstracker.dto;

import com.kiran.fitnesstracker.entity.WorkoutLog;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutLogDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long workoutPlanId;
    private String workoutPlanTitle;
    private LocalDate logdate;
    private String status;
    private String notes;
    private Long createdAt;

    public static WorkoutLogDTO fromEntity(WorkoutLog log){
        return WorkoutLogDTO.builder()
                .id(log.getId())
                .userId(log.getUser().getId())
                .userName(log.getUser().getName())
                .workoutPlanId(log.getWorkoutPlan().getId())
                .workoutPlanTitle(log.getWorkoutPlan().getTitle())
                .logdate(log.getLogDate())
                .status(log.getStatus().toString())
                .notes(log.getNotes())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
