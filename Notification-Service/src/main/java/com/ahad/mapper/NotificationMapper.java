// package com.ahad.mapper;

// import org.mapstruct.Mapper;

// @Mapper(componentModel = "spring")
// public interface NotificationMapper {

// @Mapping(target = "id", ignore = true)
// @Mapping(target = "isActive", ignore = true)
// @Mapping(target = "applications", ignore = true)
// @Mapping(target = "postedAt", ignore = true)
// Notification mapToNotification(NotificationRequestDTO dto);

// @Mapping(target = "companyId", ignore = true)
// @Mapping(target = "companyName", ignore = true)
// NotificationResponseDTO mapToNotificationResponseDTO(Notification
// notification);

// @BeanMapping(nullValuePropertyMappingStrategy =
// NullValuePropertyMappingStrategy.IGNORE)
// @Mapping(target = "id", ignore = true)
// @Mapping(target = "applications", ignore = true)
// @Mapping(target = "postedAt", ignore = true)
// @Mapping(target = "recruiterId", ignore = true)
// void toEntity(NotificationUpdateDTO dto, @MappingTarget Notification entity);

// NotificationProfileDTO mapToProfileDTO(Notification notification);

// @Mapping(target = "companyName", ignore = true)
// NotificationSearchDTO mapToSearchDTO(Notification notification);

// List<NotificationSearchDTO> mapToSearchDTOs(List<Notification> notification);

// List<NotificationResponseDTO> mapToResponseDTOs(List<Notification>
// notifications);

// }