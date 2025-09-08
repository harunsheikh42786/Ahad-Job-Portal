package com.ahad.dto.request;

import com.ahad.enums.AchievementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private LocalDate date;

    @NotNull(message = "Achievement type is required")
    private AchievementType type;
}
