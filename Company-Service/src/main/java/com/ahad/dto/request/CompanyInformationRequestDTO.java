package com.ahad.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInformationRequestDTO {

    private UUID id; // usually auto-generated, no validation needed

    @NotBlank(message = "Website link is required")
    @Size(max = 255, message = "Website link must not exceed 255 characters")
    @Pattern(regexp = "^(https?://)?([\\w.-]+)\\.[a-zA-Z]{2,}(/.*)?$", message = "Website link must be a valid URL")
    private String websiteLink;

    @NotNull(message = "Registration date is required")
    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDate registrationDate;

    @NotBlank(message = "Registration ID is required")
    @Size(max = 13, message = "Registration ID must not exceed 13 characters")
    private String registrationId;

    @NotBlank(message = "Headline is required")
    @Size(max = 255, message = "Headline must not exceed 255 characters")
    private String headline;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

}
