package com.ahad.dto.response;

import java.util.UUID;

// import com.ahad.models.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    // public static UserResponseDTO requestResponseDTO(User user) {
    // return UserResponseDTO.builder()
    // .id(user.getId())
    // .email(user.getEmail())
    // .firstName(user.getFirstName())
    // .lastName(user.getLastName())
    // .build();
    // }
}
