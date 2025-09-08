package com.ahad.dto.profile;

import com.ahad.enums.AchievementType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementProfileDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private AchievementType type;
}
