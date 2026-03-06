package com.kiran.fitnesstracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kiran.fitnesstracker.dto.WorkoutLogDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "workout_plans")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workout_title", nullable = false)
    private String title;

    @Column(name = "workout_description", length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_Id", nullable = false)
    private User trainer;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(nullable = false)
    private String difficulty; //BEGINNER, INTERMEDIATE, ADVANCED

    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(name = "created_at")
    private Long createdAt = System.currentTimeMillis();

    @Builder.Default
    @Column(name = "updated_at")
    private Long updatedAt = System.currentTimeMillis();

    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WorkoutLog> workoutLogs;
}
