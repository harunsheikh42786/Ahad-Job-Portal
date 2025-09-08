package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.profile.AchievementProfileDTO;
import com.ahad.dto.request.AchievementRequestDTO;
import com.ahad.dto.response.AchievementResponseDTO;
import com.ahad.dto.update.AchievementUpdateDTO;
import com.ahad.models.Achievement;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    // ✅ Create
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    Achievement toEntity(AchievementRequestDTO dto);

    // ✅ Update (PATCH support)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    void updateEntityFromDto(AchievementUpdateDTO dto, @MappingTarget Achievement entity);

    // ✅ Response
    AchievementResponseDTO toResponseDTO(Achievement entity);

    // ✅ Profile
    AchievementProfileDTO toProfileDTO(Achievement entity);
}
