package com.ahad.servicesImpl;

import com.ahad.dto.request.EducationRequestDTO;
import com.ahad.dto.update.EducationUpdateDTO;
import com.ahad.mapper.EducationMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.dto.response.EducationResponseDTO;
import com.ahad.models.Education;
import com.ahad.models.UserInformation;
import com.ahad.repos.EducationRepository;
import com.ahad.repos.UserInformationRepository;
import com.ahad.services.EducationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final UserInformationRepository userInformationRepository;
    private final EducationMapper educationMapper;

    @Override
    public EducationResponseDTO addEducation(EducationRequestDTO dto) {
        UserInformation userInfo = userInformationRepository.findById(dto.getUserInformationId())
                .orElseThrow(() -> new RuntimeException(
                        "UserInformation " + ResponseMessage.ID_NOT_FOUND + dto.getUserInformationId()));

        Education education = educationMapper.toEntity(dto);

        education.setUserInformation(userInfo);
        educationRepository.save(education);

        return educationMapper.toResponseDTO(education);
    }

    @Override
    public EducationResponseDTO updateEducation(UUID educationId, EducationUpdateDTO updateDTO) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new RuntimeException("Education " + ResponseMessage.ID_NOT_FOUND + educationId));

        educationMapper.updateEntityFromDto(updateDTO, education);

        educationRepository.save(education);

        return educationMapper.toResponseDTO(education);
    }

    @Override
    public void deleteEducation(UUID educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new RuntimeException("Education " + ResponseMessage.ID_NOT_FOUND + educationId));

        educationRepository.delete(education);
    }

    @Override
    public List<EducationResponseDTO> getAllEducations(UUID userInformationId) {
        return educationRepository.findByUserInformationId(
                userInformationId)
                .stream()
                .map(educationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EducationResponseDTO getEducationById(UUID educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new RuntimeException("Education " + ResponseMessage.ID_NOT_FOUND + educationId));

        return educationMapper.toResponseDTO(education);
    }

    // private EducationResponseDTO toResponseDTO(Education education) {
    // return EducationResponseDTO.builder()
    // .id(education.getId())
    // .institutionName(education.getInstitutionName())
    // .degree(education.getDegree())
    // .fieldOfStudy(education.getFieldOfStudy())
    // .startDate(education.getStartDate())
    // .endDate(education.getEndDate())
    // .currentlyStudying(education.isCurrentlyStudying())
    // .grade(education.getGrade())
    // .description(education.getDescription())
    // .build();
    // }

}
