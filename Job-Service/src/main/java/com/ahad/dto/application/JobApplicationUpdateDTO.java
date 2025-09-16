package com.ahad.dto.application;

import jakarta.validation.constraints.Size;

import lombok.*;;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationUpdateDTO {
    // Resume link or file path
    @Size(max = 300, message = "Resume URL cannot exceed 300 characters")
    private String resumeUrl;

}
