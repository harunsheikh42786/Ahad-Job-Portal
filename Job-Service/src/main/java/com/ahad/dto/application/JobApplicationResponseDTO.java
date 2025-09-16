package com.ahad.dto.application;

import com.ahad.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationResponseDTO {

    private UUID id;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

}
