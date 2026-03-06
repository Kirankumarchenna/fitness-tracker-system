package com.kiran.fitnesstracker.controller;

import com.kiran.fitnesstracker.dto.CreateWorkoutLogRequestDTO;
import com.kiran.fitnesstracker.dto.WorkoutLogDTO;
import com.kiran.fitnesstracker.dto.WorkoutPlanDTO;
import com.kiran.fitnesstracker.service.WorkoutLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.DesignMode;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workout-logs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Workout Logs", description = "Workout log management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class WorkoutLogController {

    private final WorkoutLogService workoutLogService;

    //create new workout log
    @PostMapping
    @Operation(summary = "Create workout log", description = "Log a completed, pending or skipper workout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Worklog created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutLogDTO> createWorkoutLog(
            @Valid @RequestBody CreateWorkoutLogRequestDTO request){
        log.info("Create worklog endpoint called for plan: {}", request.getWorkoutPlanId());
        WorkoutLogDTO workoutLogDTO = workoutLogService.createWorkoutLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutLogDTO);
    }

    //get workout log by ID
    @GetMapping("/{logId}")
    @Operation(summary = "Get workout log by ID", description = "Retrieve a specific workout log by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout log retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutLogDTO> getWorkoutLogById(@PathVariable Long logId){
        log.info("Get workout log endpoint called for logId: {}", logId);
        WorkoutLogDTO workoutLogDTO = workoutLogService.getWorkoutLogById(logId);
        return ResponseEntity.ok(workoutLogDTO);
    }

    //Get current users workout history
    @GetMapping("/my-history")
    @Operation(summary = "Get current users workout history", description = "Retrieve the current user's complete workout history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout history retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorkoutLogDTO>> getCurrentUsersWorkoutHistory(){
        log.info("Get current user workout history endpoint called");
        List<WorkoutLogDTO> history = workoutLogService.getCurrentUserWorkoutHistory();
        return ResponseEntity.ok(history);
    }

    //Get user's workout history by user ID
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's workout history", description = "Retrieve a user's complete workout history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout history retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    public ResponseEntity<List<WorkoutLogDTO>> getUserWorkoutHistory(@PathVariable Long userId){
        log.info("Get user workout history endpoint called for userId: {}", userId);
        List<WorkoutLogDTO> history = workoutLogService.getUserWorkoutHistory(userId);
        return ResponseEntity.ok(history);
    }

    //Get workout logs by planID
    @GetMapping("/plan/{planId}")
    @Operation(summary = "Get workout logs by plan", description = "Retrieve all the logs for the specific workout plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout history retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorkoutLogDTO>> getWorkoutLogsByPlanId(@PathVariable Long planId){
        log.info("Get workout logs by plan endpoint called for planId: {}", planId);
        List<WorkoutLogDTO> workoutLogs = workoutLogService.getWorkoutLogsByPlanId(planId);
        return ResponseEntity.ok(workoutLogs);
    }

    //Get workout logs by date range
    @GetMapping("/user/{userId}/date-range")
    @Operation(summary = "Get workout logs by date range", description = "Retrieve user's workout logs for a specific date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout history retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorkoutLogDTO>> getWorkoutLogsByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        log.info("Get workout logs by date range endpoint called for userId: {} form {} to {}", userId, startDate, endDate);
        List<WorkoutLogDTO> logs = workoutLogService.getUserWorkoutLogsByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(logs);
    }

    //Update Workout Log
    @PutMapping("/{logId}")
    @Operation(summary = "Update workout log", description = "Update an existing workout log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout log updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You can only update your own workout plan"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutLogDTO> updateWorkoutLog(
            @PathVariable Long logId, @Valid @RequestBody CreateWorkoutLogRequestDTO request){
            log.info("Update workout log endpoint called for logId: {}", logId);
            WorkoutLogDTO workoutLogDTO = workoutLogService.updateWorkoutLog(logId, request);
            return ResponseEntity.ok(workoutLogDTO);
    }

    //Delete workout log
    @DeleteMapping("/{logId}")
    @Operation(summary = "Delete workout log", description = "Delete an existing workout log")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workout log deleted successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or Invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You can only delete your own workout plan"),
            @ApiResponse(responseCode = "404", description = "Work Log not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutLogDTO> deleteWorkoutLog(@PathVariable Long logId){
        log.info("Delete workout plan endpoint called for logId: {}", logId);
        workoutLogService.deleteWorkoutLog(logId);
        return ResponseEntity.noContent().build();
    }
}
