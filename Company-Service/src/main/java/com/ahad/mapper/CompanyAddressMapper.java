package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.profile.CompanyAddressProfileDTO;
import com.ahad.dto.request.CompanyAddressRequestDTO;
import com.ahad.dto.response.CompanyAddressResponseDTO;
import com.ahad.dto.update.CompanyAddressUpdateDTO;
import com.ahad.models.CompanyAddress;

@Mapper(componentModel = "spring")
public interface CompanyAddressMapper {

    @Mapping(target = "companyInformation", ignore = true)
    CompanyAddress requestToEntity(CompanyAddressRequestDTO requestDTO);

    // @Mapping(tar)
    CompanyAddressResponseDTO entityToResponse(CompanyAddress companyAddress);

    // @Mapping(target = "employers", ignore = true)
    CompanyAddressProfileDTO entityToProfile(CompanyAddress companyAddress);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "companyInformation", ignore = true)
    void toEntity(CompanyAddressUpdateDTO dto, @MappingTarget CompanyAddress companyAddress);
}
