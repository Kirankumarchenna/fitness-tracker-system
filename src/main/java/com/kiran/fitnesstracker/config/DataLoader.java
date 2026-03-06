package com.kiran.fitnesstracker.config;

import com.kiran.fitnesstracker.dto.WorkoutLogDTO;
import com.kiran.fitnesstracker.entity.User;
import com.kiran.fitnesstracker.entity.WorkoutLog;
import com.kiran.fitnesstracker.entity.WorkoutPlan;
import com.kiran.fitnesstracker.repository.UserRepository;
import com.kiran.fitnesstracker.repository.WorkoutLogRepository;
import com.kiran.fitnesstracker.repository.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/*
    Sample data loader for testing
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData(
            UserRepository userRepository,
            WorkoutPlanRepository workoutPlanRepository,
            WorkoutLogRepository workoutLogRepository){
        return args -> {
            log.info("Loading sample data into database..");

            //Create sample users
            User admin = User.builder()
                    .name("Admin User")
                    .email("admin@fitnesstracker.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(User.Role.ADMIN)
                    .active(true)
                    .build();

            User trainer1 = User.builder()
                    .name("Kiran Trainer")
                    .email("kiran@trainer.com")
                    .password(passwordEncoder.encode("kiran123"))
                    .role(User.Role.TRAINER)
                    .active(true)
                    .build();

            User trainer2 = User.builder()
                    .name("Chenna")
                    .email("chenna@trainer.com")
                    .password(passwordEncoder.encode("chenna123"))
                    .role(User.Role.TRAINER)
                    .active(true)
                    .build();

            User user1 = User.builder()
                    .name("Noel")
                    .email("noel@user.com")
                    .password(passwordEncoder.encode("noel123"))
                    .role(User.Role.USER)
                    .active(true)
                    .build();

            User user2 = User.builder()
                    .name("John")
                    .email("john@user.com")
                    .password(passwordEncoder.encode("john123"))
                    .role(User.Role.USER)
                    .active(true)
                    .build();

            //Save Users
            admin = userRepository.save(admin);
            trainer1 = userRepository.save(trainer1);
            trainer2 = userRepository.save(trainer2);
            user1 = userRepository.save(user1);
            user2 = userRepository.save(user2);

            log.info("Sample users created successfully");

            //Create Sample workout plans
            WorkoutPlan plan1 = WorkoutPlan.builder()
                    .title("Beginner Cardio program")
                    .description("A comprehensive 30-day cardio program for beginners")
                    .trainer(trainer1)
                    .durationDays(30)
                    .difficulty("BEGINNER")
                    .active(true)
                    .build();

            WorkoutPlan plan2 = WorkoutPlan.builder()
                    .title("Advanced Strength training")
                    .description("12-week strength building program with progressive overload")
                    .trainer(trainer1)
                    .durationDays(84)
                    .difficulty("ADVANCED")
                    .active(true)
                    .build();

            WorkoutPlan plan3 = WorkoutPlan.builder()
                    .title("Intermediate Weight loss")
                    .description("8-week program combining cardio and strength for weight loss")
                    .trainer(trainer1)
                    .durationDays(56)
                    .difficulty("INTERMEDIATE")
                    .active(true)
                    .build();

            plan1 = workoutPlanRepository.save(plan1);
            plan2 = workoutPlanRepository.save(plan2);
            plan3 = workoutPlanRepository.save(plan3);

            log.info("Sample workout plans created successfully");

            //Create sample workout logs
            WorkoutLog log1 = WorkoutLog.builder()
                    .user(user1)
                    .workoutPlan(plan1)
                    .logDate(LocalDate.now())
                    .status(WorkoutLog.Status.COMPLETED)
                    .notes("Great session! Completed all exercises with proper form")
                    .build();

            WorkoutLog log2 = WorkoutLog.builder()
                    .user(user1)
                    .workoutPlan(plan2)
                    .logDate(LocalDate.now())
                    .status(WorkoutLog.Status.COMPLETED)
                    .notes("Felt strong today, increased slightly")
                    .build();

            WorkoutLog log3 = WorkoutLog.builder()
                    .user(user2)
                    .workoutPlan(plan3)
                    .logDate(LocalDate.now())
                    .status(WorkoutLog.Status.COMPLETED)
                    .notes("Planning to do this evening")
                    .build();

            log1 = workoutLogRepository.save(log1);
            log2 = workoutLogRepository.save(log2);
            log3 = workoutLogRepository.save(log3);

            log.info("Sample workout logs created successfully");
            log.info("Sample data loading completed");
        };
    }
}
