package com.kiran.fitnesstracker.repository;

import com.kiran.fitnesstracker.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan>findByTrainerId(Long trainerId);
    List<WorkoutPlan>findByActiveTrue();
}
