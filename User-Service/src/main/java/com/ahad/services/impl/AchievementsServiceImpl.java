package com.ahad.services.impl;

import com.ahad.dto.request.AchievementRequestDTO;
import com.ahad.dto.response.AchievementResponseDTO;
import com.ahad.dto.update.AchievementUpdateDTO;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.AchievementMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Achievement;
import com.ahad.models.UserInformation;
import com.ahad.repos.AchievementRepository;
import com.ahad.repos.UserInformationRepository;
import com.ahad.services.internal.AchievementService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AchievementsServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserInformationRepository userInformationRepository;
    private final AchievementMapper achievementMapper;

    @Override
    public AchievementResponseDTO getAchievementById(UUID achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Achievement " + ResponseMessage.ID_NOT_FOUND + achievementId));

        return achievementMapper.entityToResponse(achievement);
    }

    @Override
    public AchievementResponseDTO createAchievement(AchievementRequestDTO achievementRequestDTO) {
        UserInformation userInfo = userInformationRepository
                .findById(UUID.fromString(achievementRequestDTO.getUserInformationId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserInformation " + ResponseMessage.ID_NOT_FOUND
                                + achievementRequestDTO.getUserInformationId()));

        Achievement achievement = achievementMapper.requestToEntity(achievementRequestDTO);
        achievement.setUserInformation(userInfo);

        return achievementMapper.entityToResponse(achievementRepository.save(achievement));
    }

    @Override
    public AchievementResponseDTO updateAchievement(UUID achievementId, AchievementUpdateDTO achievementUpdateDTO) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Achievement " + ResponseMessage.ID_NOT_FOUND + achievementId));

        achievementMapper.updateEntityFromDto(achievementUpdateDTO, achievement);
        return achievementMapper.entityToResponse(achievementRepository.save(achievement));
    }

    @Override
    public void deleteAchievement(UUID achievementId) {
        if (!achievementRepository.existsById(achievementId)) {
            throw new ResourceNotFoundException(
                    "Achievement " + ResponseMessage.ID_NOT_FOUND + achievementId);
        }
        achievementRepository.deleteById(achievementId);
    }

    @Override
    public List<AchievementResponseDTO> getAchievementsByUserInformationId(UUID userInformationId) {
        List<Achievement> achievements = achievementRepository.findByUserInformationId(userInformationId);

        if (achievements.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Achievements " + ResponseMessage.NO_DATA + "UserInformation : " + userInformationId);
        }
        List<AchievementResponseDTO> achievementResponseDTOs = achievements.stream()
                .map(achievementMapper::entityToResponse).toList();
        return achievementResponseDTOs;
    }

}
