package com.kiran.fitnesstracker.controller;

import com.kiran.fitnesstracker.dto.CreateWorkoutPlanRequestDTO;
import com.kiran.fitnesstracker.dto.WorkoutLogDTO;
import com.kiran.fitnesstracker.dto.WorkoutPlanDTO;
import com.kiran.fitnesstracker.entity.WorkoutLog;
import com.kiran.fitnesstracker.entity.WorkoutPlan;
import com.kiran.fitnesstracker.service.WorkoutPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/workout-plans")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Workout plans", description = "Workout plan management endpoint")
@SecurityRequirement(name = "Bearer Authentication")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    //create new workout plan (Trainer only)
    @PostMapping
    @PreAuthorize("hasRole('TRAINER')")
    @Operation(summary = "Create a new workout plan", description = "Create a new workout plan (TRAINER role required)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout plan successfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Only trainers can create workout plans"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutPlanDTO> createWorkoutPlan(@Valid @RequestBody CreateWorkoutPlanRequestDTO request){
        log.info("Create workout plan endpoint called with title: {}", request.getTitle());

        WorkoutPlanDTO workoutPlanDTO = workoutPlanService.createWorkoutPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutPlanDTO);
    }

    //get workout plan by ID
    @GetMapping("/{planId}")
    @Operation(summary = "Get workout plan by ID", description = "Retrieve a specific workout plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout plan successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Workout plan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutPlanDTO> getWorkoutPlanById(@PathVariable Long planId){
        log.info("Get workout plan endpoint called for planId: {}", planId);
        WorkoutPlanDTO workoutPlanDTO = workoutPlanService.getWorkoutPlanById(planId);
        return ResponseEntity.ok(workoutPlanDTO);
    }

    //get all active workout plans
    @GetMapping
    @Operation(summary = "Get all active workout plans", description = "Retrieve all active workout plans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout plans successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorkoutPlanDTO>> getAllActiveWorkoutPlans(){
        log.info("Get all active workout plans endpoint called");
        List<WorkoutPlanDTO> workoutPlans = workoutPlanService.getAllActiveWorkoutPlans();
        return ResponseEntity.ok(workoutPlans);
    }

    //get workout plans by trainer ID
    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Get workout plans by trainer", description = "Retrieve all workout plans created by a specific trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout plans successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token is missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WorkoutPlanDTO>> getWorkoutPlansByTrainerId(@PathVariable Long trainerId){
        log.info("Get workout plans by trainer endpoint called for trainerId: {}", trainerId);
        List<WorkoutPlanDTO> workoutPlans = workoutPlanService.getWorkoutPlansByTrainerId(trainerId);
        return ResponseEntity.ok(workoutPlans);
    }

    //update workout plan (Trainer only)
    @PutMapping("/{planId}")
    @PreAuthorize("hasRole('TRAINER')")
    @Operation(summary = "Update workout plan", description = "Update an existing workout plan (TRAINER role required)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout plan updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WorkoutLogDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized -JWT token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Only the creator can update this plan"),
            @ApiResponse(responseCode = "404", description = "Workout plan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WorkoutPlanDTO> updateWorkoutPlan(
            @PathVariable Long planId, @Valid @RequestBody CreateWorkoutPlanRequestDTO request){
        log.info("Update workout plan endpoint called for planId: {}", planId);
        WorkoutPlanDTO workoutPlanDTO = workoutPlanService.updateWorkoutPlan(planId, request);
        return ResponseEntity.ok(workoutPlanDTO);
    }

    //Delete workout plan (Trainer only)
    @DeleteMapping("/{planId}")
    @PreAuthorize("hasRole('TRAINER')")
    @Operation(summary = "Delete workout plan", description = "Delete an existing workout plan (TRAINER role required)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workout plan deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized -JWT token is missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Only the creator can delete this plan"),
            @ApiResponse(responseCode = "404", description = "Workout plan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    public ResponseEntity<WorkoutPlanDTO> deleteWorkoutPlan(@PathVariable Long planId){
        log.info("Delete workout plan endpoint called for planId: {}", planId);
        workoutPlanService.deleteWorkoutPlan(planId);
        return ResponseEntity.noContent().build();
    }
}
