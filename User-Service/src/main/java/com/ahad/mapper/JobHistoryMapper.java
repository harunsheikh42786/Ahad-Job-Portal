package com.ahad.mapper;

import com.ahad.dto.profile.JobHistoryProfileDTO;
import com.ahad.dto.request.JobHistoryRequestDTO;
import com.ahad.dto.response.JobHistoryResponseDTO;
import com.ahad.dto.update.JobHistoryUpdateDTO;
import com.ahad.models.JobHistory;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface JobHistoryMapper {

    JobHistoryResponseDTO toDto(JobHistory jobHistory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    JobHistory toEntity(JobHistoryRequestDTO jobHistoryRequestDTO);

    // ✅ UpdateDTO -> existing Entity (partial update, nulls ignored)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    void updateEntityFromDto(JobHistoryUpdateDTO dto, @MappingTarget JobHistory entity);

    JobHistoryProfileDTO toProfileDto(JobHistory jobHistory);

}
