package com.ahad.dto.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String headline;
    private String contactNumber;
    private String dob;
    private String gender;
    private String password;
    private String role;
    private UserInformationUpdateDTO userInformationUpdateDTO;
}
