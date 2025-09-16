package com.ahad.mapper;

import com.ahad.dto.profile.UserInformationProfileDTO;
import com.ahad.dto.request.UserInformationRequestDTO;
import com.ahad.dto.update.UserInformationUpdateDTO;
import com.ahad.dto.response.UserInformationResponseDTO;
import com.ahad.models.UserInformation;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = { EducationMapper.class, JobHistoryMapper.class, AddressMapper.class })
public interface UserInformationMapper {

    // ✅ RequestDTO -> Entity
    @Mapping(target = "id", ignore = true) // ID auto-generated
    @Mapping(target = "achievements", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "educations", ignore = true)
    @Mapping(target = "jobHistories", ignore = true)
    @Mapping(target = "user", ignore = true)
    UserInformation toEntity(UserInformationRequestDTO dto);

    // ✅ UpdateDTO -> existing Entity (partial update, nulls ignored)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "educations", ignore = true)
    @Mapping(target = "jobHistories", ignore = true)
    void updateEntityFromDto(UserInformationUpdateDTO dto, @MappingTarget UserInformation entity);

    // ✅ Entity -> ResponseDTO
    UserInformationResponseDTO toResponseDTO(UserInformation entity);

    // ✅ Entity -> General DTO (for profile or nested usage)
    @Mapping(target = "achievements", source = "entity.achievements")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "jobHistories", source = "entity.jobHistories")
    @Mapping(target = "educations", source = "entity.educations")
    @Mapping(target = "appliedJobs", ignore = true) // To be set in service layer
    UserInformationProfileDTO toDTO(UserInformation entity);
}
