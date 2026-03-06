package com.kiran.fitnesstracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default  //lombok will understand to set the default value when the builder doesn't explicitly set.
    @Column(name = "created_at")
    private Long createdAt = System.currentTimeMillis();

    //User Roles
    public enum Role{
        USER,
        TRAINER,
        ADMIN
    }
}
