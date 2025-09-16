package com.ahad.mapper;

import com.ahad.dto.request.AchievementRequestDTO;
import com.ahad.dto.response.AchievementResponseDTO;
import com.ahad.dto.update.AchievementUpdateDTO;
import com.ahad.models.Achievement;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true) // set manually in service
    Achievement requestToEntity(AchievementRequestDTO dto);

    AchievementResponseDTO entityToResponse(Achievement entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    void updateEntityFromDto(AchievementUpdateDTO dto, @MappingTarget Achievement entity);
}
