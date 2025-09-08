package com.ahad.dto.response;

import com.ahad.enums.AchievementType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private AchievementType type;
}
