package com.kiran.fitnesstracker.repository;

import com.kiran.fitnesstracker.entity.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByUserId(Long userId);
    List<WorkoutLog> findByWorkoutPlanId(Long workoutPlanId);
    List<WorkoutLog> findByUserIdAndLogDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
