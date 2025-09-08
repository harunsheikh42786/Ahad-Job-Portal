package com.ahad.dto.profile;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String role;
    private String dob;

    private UserInformationProfileDTO userInformation; // Extra profile info

    private boolean active;
}
