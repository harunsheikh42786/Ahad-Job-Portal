package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.exports.CompanySearchDTO;
import com.ahad.dto.profile.CompanyProfileDTO;
import com.ahad.dto.request.CompanyRequestDTO;
import com.ahad.dto.response.CompanyResponseDTO;
import com.ahad.dto.update.CompanyUpdateDTO;
import com.ahad.models.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "open", ignore = true)
    @Mapping(target = "companyInformation", ignore = true)
    Company maptoCompany(CompanyRequestDTO dto);

    CompanyResponseDTO mapToCompanyResponseDTO(Company company);

    // ✅ just map companyInformation → companyInformationDTO
    @Mapping(target = "companyInformationDTO", source = "company.companyInformation")
    @Mapping(target = "companyInformationDTO.employers", ignore = true)
    // @Mapping(target = "companyInformationDTO", ignore = true)
    CompanyProfileDTO mapToProfileDTO(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "companyInformation", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    void toEntity(CompanyUpdateDTO dto, @MappingTarget Company company);

    // For Client Service'

    // Assuming companyInformation.addresses is a List and you want the city of the
    // first address
    @Mapping(target = "headline", source = "company.companyInformation.headline")
    CompanySearchDTO mapToCompanySearchDTO(Company company);
}
