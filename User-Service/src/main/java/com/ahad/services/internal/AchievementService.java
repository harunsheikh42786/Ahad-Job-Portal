package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.request.AchievementRequestDTO;
import com.ahad.dto.response.AchievementResponseDTO;
import com.ahad.dto.update.AchievementUpdateDTO;

public interface AchievementService {
    AchievementResponseDTO createAchievement(AchievementRequestDTO achievementRequestDTO);

    AchievementResponseDTO getAchievementById(UUID achievementId);

    List<AchievementResponseDTO> getAchievementsByUserInformationId(UUID userInformationId);

    AchievementResponseDTO updateAchievement(UUID achievementId, AchievementUpdateDTO achievementUpdateDTO);

    void deleteAchievement(UUID achievementId);
}
