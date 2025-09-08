package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.request.AddressRequestDTO;
import com.ahad.dto.update.AddressUpdateDTO;
import com.ahad.dto.response.AddressResponseDTO;
import com.ahad.dto.profile.AddressProfileDTO;
import com.ahad.models.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    // ✅ Request DTO → Entity (new address create)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true) // User set manually in service
    Address toEntity(AddressRequestDTO dto);

    // ✅ Update DTO → Existing Entity (partial update, null ignored)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userInformation", ignore = true)
    void updateEntityFromDto(AddressUpdateDTO dto, @MappingTarget Address entity);

    // ✅ Entity → Response DTO
    AddressResponseDTO toResponseDTO(Address entity);

    // ✅ Entity → Profile DTO (nested usage)
    AddressProfileDTO toProfileDTO(Address entity);
}
