package com.kiran.fitnesstracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "workout_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    private WorkoutPlan workoutPlan;

    @Column(nullable = false)
    private LocalDate logDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(length = 500)
    private String notes;

    @Builder.Default
    @Column(name = "created_at")
    private Long createdAt = System.currentTimeMillis();

    public enum Status{
        PENDING,
        COMPLETED,
        SKIPPED
    }
}
