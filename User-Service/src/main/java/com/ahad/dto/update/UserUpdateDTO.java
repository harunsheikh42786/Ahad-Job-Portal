package com.ahad.dto.update;

import com.ahad.enums.JobStatus;

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
    private String contactNumber;
    private String dob;
    private String gender;
    private String password;
    private String role;
    private String headline;
    private String portfolio;
    private JobStatus jobStatus;
}
