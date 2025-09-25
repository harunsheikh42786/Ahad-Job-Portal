package com.ahad.controllers;

import com.ahad.dto.request.AchievementRequestDTO;
import com.ahad.dto.response.AchievementResponseDTO;
import com.ahad.dto.update.AchievementUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.AchievementService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.V1 + "/achievements")
@RequiredArgsConstructor
public class AchievementController {

        private final AchievementService achievementService;

        // 🔹 Create Achievement
        @PostMapping
        public ResponseEntity<ApiResponse<AchievementResponseDTO>> createAchievement(
                        @RequestBody AchievementRequestDTO dto) {

                AchievementResponseDTO created = achievementService.createAchievement(dto);

                ApiResponse<AchievementResponseDTO> response = ApiResponse.<AchievementResponseDTO>builder()
                                .success(true)
                                .message("Achievement " + ResponseMessage.CREATED)
                                .data(created)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .build();

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        // 🔹 Get Achievement by Id
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<AchievementResponseDTO>> getAchievementById(@PathVariable UUID id) {

                AchievementResponseDTO achievement = achievementService.getAchievementById(id);

                ApiResponse<AchievementResponseDTO> response = ApiResponse.<AchievementResponseDTO>builder()
                                .success(true)
                                .message("Achievement " + ResponseMessage.FETCHED)
                                .data(achievement)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // 🔹 Get Achievements by UserInformationId (returns list)
        @GetMapping("/user-info/{userInfoId}")
        public ResponseEntity<ApiResponse<List<AchievementResponseDTO>>> getAchievementsByUserInformationId(
                        @PathVariable UUID userInfoId) {

                List<AchievementResponseDTO> achievements = achievementService
                                .getAchievementsByUserInformationId(userInfoId);

                ApiResponse<List<AchievementResponseDTO>> response = ApiResponse.<List<AchievementResponseDTO>>builder()
                                .success(true)
                                .message("Achievements " + ResponseMessage.FETCHED)
                                .data(achievements)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // 🔹 Update Achievement
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<AchievementResponseDTO>> updateAchievement(
                        @PathVariable UUID id,
                        @RequestBody AchievementUpdateDTO dto) {

                AchievementResponseDTO updated = achievementService.updateAchievement(id, dto);

                ApiResponse<AchievementResponseDTO> response = ApiResponse.<AchievementResponseDTO>builder()
                                .success(true)
                                .message("Achievement " + ResponseMessage.UPDATED)
                                .data(updated)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // 🔹 Delete Achievement
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteAchievement(@PathVariable UUID id) {
                achievementService.deleteAchievement(id);

                ApiResponse<Void> response = ApiResponse.<Void>builder()
                                .success(true)
                                .message("Achievement " + ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }
}
