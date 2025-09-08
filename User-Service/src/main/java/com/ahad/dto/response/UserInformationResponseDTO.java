package com.ahad.dto.response;

import com.ahad.enums.JobStatus;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformationResponseDTO {
    private UUID id;
    private String headline;
    private String portfolio;
    private JobStatus jobStatus;
}
