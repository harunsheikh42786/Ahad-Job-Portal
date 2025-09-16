package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.profile.UserProfileDTO;
import com.ahad.dto.request.UserRequestDTO;
import com.ahad.dto.response.UserResponseDTO;
import com.ahad.dto.response.UserSearchDTO;
import com.ahad.dto.update.UserUpdateDTO;
import com.ahad.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    User toUserEntity(UserRequestDTO dto);

    @Mapping(target = "active", source = "user.active")
    @Mapping(target = "role", expression = "java(user.getRole() != null ? user.getRole().toString() : null)")
    @Mapping(target = "userInformation", source = "user.userInformation")
    @Mapping(target = "userInformation.appliedJobs", ignore = true) // To be set in service layer
    UserProfileDTO toProfileDTO(User user);

    UserResponseDTO toResponseDto(User user);

    // ✅ Update DTO → Existing Entity (partial update, null ignored)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "userInformation.headline", source = "headline")
    @Mapping(target = "userInformation.portfolio", source = "portfolio")
    @Mapping(target = "userInformation.jobStatus", source = "jobStatus")
    void toUserEntity(UserUpdateDTO dto, @MappingTarget User entity);

    // 👇 Search DTO Mapping
    @Mapping(target = "headline", source = "user.userInformation.headline")
    UserSearchDTO toSearchDTO(User user);

    // 👇 Search DTO Mapping
    @Mapping(target = "headline", source = "user.userInformation.headline")
    UserSearchDTO toSearchDTOExternal(User user);

}
