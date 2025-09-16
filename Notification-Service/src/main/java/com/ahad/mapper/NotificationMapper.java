package com.ahad.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.profile.NotificationProfileDTO;
import com.ahad.dto.request.NotificationRequestDTO;
import com.ahad.dto.response.NotificationResponseDTO;
import com.ahad.dto.update.NotificationUpdateDTO;
import com.ahad.models.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "isBroadcast", source = "broadcast")
    @Mapping(target = "isCompany", source = "company")
    @Mapping(target = "isRead", source = "read")
    Notification toEntity(NotificationRequestDTO dto);

    @Mapping(target = "isRead", source = "read")
    NotificationResponseDTO toResponseDto(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "receiverId", ignore = true)
    @Mapping(target = "broadcast", ignore = true)
    void toEntity(NotificationUpdateDTO dto, @MappingTarget Notification entity);

    NotificationProfileDTO toProfileDto(Notification notification);

    List<NotificationResponseDTO> mapToResponseDTOs(List<Notification> notifications);

}