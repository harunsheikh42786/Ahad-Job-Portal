package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.CompanyProfileDTO;
import com.ahad.dto.CompanyRequestDTO;
import com.ahad.dto.CompanyResponseDTO;
import com.ahad.dto.CompanyUpdateDTO;
import com.ahad.models.Company;
import com.ahad.models.CompanyInformation;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "open", ignore = true)
    @Mapping(target = "companyInformation", ignore = true)
    Company maptoCompany(CompanyRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "headline", ignore = true)
    @Mapping(target = "websiteLink", ignore = true)
    CompanyInformation maptoCompanyInformation(CompanyRequestDTO dto);

    CompanyResponseDTO mapToCompanyResponseDTO(Company company);

    @Mapping(target = "websiteLink", source = "company.companyInformation.websiteLink")
    @Mapping(target = "description", source = "company.companyInformation.description")
    @Mapping(target = "headline", source = "company.companyInformation.headline")
    @Mapping(target = "registrationId", source = "company.companyInformation.registrationId")
    @Mapping(target = "registrationDate", source = "company.companyInformation.registrationDate")
    @Mapping(target = "addresses", source = "company.companyInformation.addresses")
    @Mapping(target = "jobPosts", ignore = true)
    @Mapping(target = "employers", ignore = true)
    CompanyProfileDTO mapToProfileDTO(Company company);

    // @Mapping(target = "addresses", ignore = true)
    // @Mapping(target = "contactNumber", ignore = true)
    // @Mapping(target = "email", ignore = true)
    // @Mapping(target = "employers", ignore = true)
    // @Mapping(target = "jobPosts", ignore = true)
    // @Mapping(target = "name", ignore = true)
    // @Mapping(target = "open", ignore = true)
    // CompanyProfileDTO mapToProfileDTO(CompanyInformation companyInformation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "companyInformation", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    void toEntity(CompanyUpdateDTO dto, @MappingTarget Company company);

}
