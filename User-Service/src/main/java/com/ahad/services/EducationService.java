package com.ahad.services;

import com.ahad.dto.request.EducationRequestDTO;
import com.ahad.dto.update.EducationUpdateDTO;
import com.ahad.dto.response.EducationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface EducationService {
    EducationResponseDTO addEducation(EducationRequestDTO requestDTO);

    EducationResponseDTO updateEducation(UUID educationId, EducationUpdateDTO updateDTO);

    void deleteEducation(UUID educationId);

    List<EducationResponseDTO> getAllEducations(UUID userId);

    EducationResponseDTO getEducationById(UUID educationId);
}
