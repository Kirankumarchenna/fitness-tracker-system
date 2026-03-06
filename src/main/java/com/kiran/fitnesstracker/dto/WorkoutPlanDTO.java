package com.kiran.fitnesstracker.dto;

import com.kiran.fitnesstracker.entity.WorkoutPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutPlanDTO {
    private Long id;
    private String title;
    private String description;
    private String trainerName;
    private Long trainerId;
    private Integer durationDays;
    private String difficulty;
    private Boolean active;
    private Long createdAt;
    private Long updatedAt;

    public static WorkoutPlanDTO fromEntity(WorkoutPlan plan){
        return WorkoutPlanDTO.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .description(plan.getDescription())
                .trainerName(plan.getTrainer().getName())
                .trainerId(plan.getTrainer().getId())
                .durationDays(plan.getDurationDays())
                .difficulty(plan.getDifficulty())
                .active(plan.getActive())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }
}
