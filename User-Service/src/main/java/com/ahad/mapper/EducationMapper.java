package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.profile.EducationProfileDTO;
import com.ahad.dto.request.EducationRequestDTO;
import com.ahad.dto.response.EducationResponseDTO;
import com.ahad.dto.update.EducationUpdateDTO;
import com.ahad.models.Education;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    // ✅ Create
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    Education toEntity(EducationRequestDTO dto);

    // ✅ Update DTO → Existing Entity (partial update, null ignored)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    void updateEntityFromDto(EducationUpdateDTO dto, @MappingTarget Education entity);

    // ✅ Response
    EducationResponseDTO toResponseDTO(Education entity);

    // ✅ Profile
    EducationProfileDTO toProfileDTO(Education entity);
}
