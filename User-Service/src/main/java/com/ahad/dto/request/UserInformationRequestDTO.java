package com.ahad.dto.request;

import com.ahad.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformationRequestDTO {

    @NotBlank(message = "Headline is required")
    @Size(max = 50, message = "Headline can be at most 50 characters long")
    private String headline;

    @NotBlank(message = "Portfolio link is required")
    @Size(max = 100, message = "Portfolio link can be at most 100 characters long")
    private String portfolio;

    @NotNull(message = "Job status is required")
    private JobStatus jobStatus;

    @NotNull(message = "User ID is required")
    private String userId; // <-- sirf ID bhejna, poora entity nahi
}
