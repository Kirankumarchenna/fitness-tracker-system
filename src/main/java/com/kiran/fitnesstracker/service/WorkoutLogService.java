package com.kiran.fitnesstracker.service;

import com.kiran.fitnesstracker.dto.CreateWorkoutLogRequestDTO;
import com.kiran.fitnesstracker.dto.WorkoutLogDTO;
import com.kiran.fitnesstracker.entity.User;
import com.kiran.fitnesstracker.entity.WorkoutLog;
import com.kiran.fitnesstracker.entity.WorkoutPlan;
import com.kiran.fitnesstracker.exception.BadRequestException;
import com.kiran.fitnesstracker.exception.ResourceNotFoundException;
import com.kiran.fitnesstracker.exception.UnauthorizedException;
import com.kiran.fitnesstracker.repository.UserRepository;
import com.kiran.fitnesstracker.repository.WorkoutLogRepository;
import com.kiran.fitnesstracker.repository.WorkoutPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public WorkoutLogDTO createWorkoutLog(CreateWorkoutLogRequestDTO request){
        log.info("Creating workout log for workout plan: {}", request.getWorkoutPlanId());

        //Get currrent user
        Long userId = userService.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        //Get workout plan
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(request.getWorkoutPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", request.getWorkoutPlanId()));

        //Validate status
        WorkoutLog.Status status;
        try{
            status = WorkoutLog.Status.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status: "+request.getStatus());
        }

        //Create workout log
        WorkoutLog workoutLog = WorkoutLog.builder()
                .user(user)
                .workoutPlan(workoutPlan)
                .logDate(request.getLogDate())
                .status(status)
                .notes(request.getNotes())
                .build();

        WorkoutLog savedLog = workoutLogRepository.save(workoutLog);
        log.info("Workout log created with ID: {}", savedLog.getId());

        return WorkoutLogDTO.fromEntity(savedLog);
    }

    //Get workout log by ID
    public WorkoutLogDTO getWorkoutLogById(Long logId){
        log.debug("Fetching workout log with ID: {}", logId);

        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(()-> new ResourceNotFoundException("WorkoutLog", "id", logId));

        return WorkoutLogDTO.fromEntity(log);
    }

    //Get user's workout history
    public List<WorkoutLogDTO> getUserWorkoutHistory(Long userId){
        log.debug("Fetching workout history for user: {}", userId);

        //verify user exists
        userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        return workoutLogRepository.findByUserId(userId).stream()
                .map(WorkoutLogDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //Get current user's workout history
    public List<WorkoutLogDTO> getCurrentUserWorkoutHistory(){
        Long userId = userService.getCurrentUserId();
        return getUserWorkoutHistory(userId);
    }

    //Get workout logs by workout plan ID
    public List<WorkoutLogDTO> getWorkoutLogsByPlanId(Long planId) {
        log.debug("Fetching workout logs for plan: {}", planId);

        //verify plan exists
        workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan","id", planId));

        return workoutLogRepository.findByWorkoutPlanId(planId).stream()
                .map(WorkoutLogDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //Get workout logs for a date range
    public List<WorkoutLogDTO> getUserWorkoutLogsByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        log.debug("Fetching workout logs for user {} between {} and {}", userId, startDate, endDate);

        //Verify user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return workoutLogRepository.findByUserIdAndLogDateBetween(userId, startDate, endDate).stream()
                .map(WorkoutLogDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //Update workout log
    public WorkoutLogDTO updateWorkoutLog(Long logId, CreateWorkoutLogRequestDTO request) {
        log.info("Updating workout workoutLoglog with ID: {}", logId);

        WorkoutLog workoutLoglog = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutLog", "id", logId));

        //verify current user owns this workoutLoglog
        Long currentUserId = userService.getCurrentUserId();
        if(!workoutLoglog.getUser().getId().equals(currentUserId)){
            throw new UnauthorizedException("You can only update your own workout logs");
        }

        //validate Status
        WorkoutLog.Status status;
        try{
            status = WorkoutLog.Status.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status: "+ request.getStatus());
        }

        workoutLoglog.setLogDate(request.getLogDate());
        workoutLoglog.setStatus(status);
        workoutLoglog.setNotes(request.getNotes());

        WorkoutLog updatedLog = workoutLogRepository.save(workoutLoglog);
        log.info("Workout workoutLoglog updated with ID: {}", logId);

        return WorkoutLogDTO.fromEntity(updatedLog);
    }

    //Delete workout log
    public void deleteWorkoutLog(Long logId){
        log.info("Deleting workout log with ID: {}", logId);

        WorkoutLog workoutLog = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutLog", "id", logId));

        //verify current user owns this log
        Long currentUserId = userService.getCurrentUserId();
        if(!workoutLog.getUser().equals(currentUserId)){
            throw new UnauthorizedException("You can only delete your own workout logs");
        }

        workoutLogRepository.delete(workoutLog);
        log.info("Workout log deleted with ID: {}", logId);
    }
}
