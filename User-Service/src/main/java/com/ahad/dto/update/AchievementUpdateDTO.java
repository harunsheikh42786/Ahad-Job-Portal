package com.ahad.dto.update;

import com.ahad.enums.AchievementType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementUpdateDTO {

    private String title;

    private String description;

    private LocalDate date;

    private AchievementType type; // nullable → update only if provided
}
