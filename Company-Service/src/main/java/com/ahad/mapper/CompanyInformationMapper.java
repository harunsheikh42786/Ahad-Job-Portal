package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.profile.CompanyInformationProfileDTO;
import com.ahad.dto.request.CompanyInformationRequestDTO;
import com.ahad.dto.response.CompanyInformationResponseDTO;
import com.ahad.dto.update.CompanyInformationUpdateDTO;
import com.ahad.models.CompanyInformation;

@Mapper(componentModel = "spring")
public interface CompanyInformationMapper {

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "addresses", ignore = true) // when creating
    CompanyInformation requestToEntity(CompanyInformationRequestDTO requestDTO);

    CompanyInformationResponseDTO entityToResponse(CompanyInformation companyInformation);

    @Mapping(target = "employers", ignore = true) // if you don’t have mapping yet
    CompanyInformationProfileDTO entityToProfile(CompanyInformation companyInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    void toEntity(CompanyInformationUpdateDTO dto, @MappingTarget CompanyInformation company);
}
