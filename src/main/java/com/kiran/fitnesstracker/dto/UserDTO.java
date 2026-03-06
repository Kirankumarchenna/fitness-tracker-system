package com.kiran.fitnesstracker.dto;

import com.kiran.fitnesstracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Boolean active;
    private Long createdAt;

    //convert user entity to DTO
    public static UserDTO fromEntity(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
