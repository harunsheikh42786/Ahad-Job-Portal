package com.ahad.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateDTO {

    @Size(max = 100, message = "Company name must not exceed 100 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    private String password;

    private String websiteLink;

    private String hedline;

    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate registrationDate;

    private String registrationId;

    private Boolean open; // allow closing/re-opening company
}
