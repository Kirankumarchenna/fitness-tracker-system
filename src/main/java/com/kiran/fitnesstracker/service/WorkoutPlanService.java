package com.kiran.fitnesstracker.service;

import com.kiran.fitnesstracker.dto.CreateWorkoutLogRequestDTO;
import com.kiran.fitnesstracker.dto.CreateWorkoutPlanRequestDTO;
import com.kiran.fitnesstracker.dto.WorkoutPlanDTO;
import com.kiran.fitnesstracker.entity.User;
import com.kiran.fitnesstracker.entity.WorkoutLog;
import com.kiran.fitnesstracker.entity.WorkoutPlan;
import com.kiran.fitnesstracker.exception.ResourceNotFoundException;
import com.kiran.fitnesstracker.exception.UnauthorizedException;
import com.kiran.fitnesstracker.repository.UserRepository;
import com.kiran.fitnesstracker.repository.WorkoutPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    //Create a new workout plan (Trainer only)
    public WorkoutPlanDTO createWorkoutPlan(CreateWorkoutPlanRequestDTO request){
        log.info("Creating new workout plan: {}", request.getTitle());

        if(!userService.isCurrentUserTrainer()){
            throw new UnauthorizedException("Only trainers can create workout plans");
        }

        //Get current trainer
        Long trainerId = userService.getCurrentUserId();
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(()->new ResourceNotFoundException("Trainer", "id", trainerId));

        //create workout plan
        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .trainer(trainer)
                .durationDays(request.getDurationDays())
                .difficulty(request.getDifficulty())
                .active(true)
                .build();

        WorkoutPlan savedPlan = workoutPlanRepository.save(workoutPlan);
        log.info("Workout plan created with ID: {}", savedPlan.getId());

        return WorkoutPlanDTO.fromEntity(savedPlan);
    }

    //get workout plan by ID
    public WorkoutPlanDTO getWorkoutPlanById(Long planId){
        log.debug("Fetching workout plan with ID: {}", planId);

        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", planId));

        return WorkoutPlanDTO.fromEntity(plan);
    }

    //get all active workout plans
    public List<WorkoutPlanDTO> getAllActiveWorkoutPlans(){
        log.debug("Fetching all active workout plans");

        return workoutPlanRepository.findByActiveTrue().stream()
                .map(WorkoutPlanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //get workout plans by trainer ID
    public List<WorkoutPlanDTO> getWorkoutPlansByTrainerId(Long trainerId){
        log.debug("Fetching workout plans for trainer: {}", trainerId);

        //verify trainer exists
        userRepository.findById(trainerId)
                .filter(user -> user.getRole() == User.Role.TRAINER)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer", "id", trainerId));

        return workoutPlanRepository.findByTrainerId(trainerId).stream()
                .map(WorkoutPlanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //update workout plan
    public WorkoutPlanDTO updateWorkoutPlan(Long planId, CreateWorkoutPlanRequestDTO request){
        log.info("Updating workout plan with ID: {}", planId);

        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", planId));

        //verify current user is trainer who created this plan
        Long currentUserId = userService.getCurrentUserId();
        if(!plan.getTrainer().getId().equals(currentUserId)){
            throw new UnauthorizedException("You can only update your own workout plans");
        }

        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.setDurationDays(request.getDurationDays());
        plan.setDifficulty(request.getDifficulty());
        plan.setUpdatedAt(System.currentTimeMillis());

        WorkoutPlan updatePlan = workoutPlanRepository.save(plan);
        log.info("Workout plan updated with ID: {}", planId);

        return WorkoutPlanDTO.fromEntity(updatePlan);
    }

    //delete workout plan
    public void deleteWorkoutPlan(Long planId){
        log.info("Deleting workout plan with ID: {}", planId);

        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", planId));

        //verify current user is the trainer who created this plan
        Long currentUserId = userService.getCurrentUserId();
        if(!plan.getTrainer().getId().equals(currentUserId)){
            throw new UnauthorizedException("You can only delete your own workout plan");
        }

        workoutPlanRepository.delete(plan);
        log.info("Workout plan deleted with ID: {}", planId);
    }
}
