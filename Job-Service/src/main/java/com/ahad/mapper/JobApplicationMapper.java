package com.ahad.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

import com.ahad.dto.application.JobApplicationRequestDTO;
import com.ahad.dto.application.JobApplicationResponseDTO;
import com.ahad.dto.application.JobApplicationUpdateDTO;
import com.ahad.dto.exports.JobApplicationForCompanyDTO;
import com.ahad.dto.exports.JobApplicationForUserDTO;
import com.ahad.events.JobApplicationEvent;
import com.ahad.models.JobApplication;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    JobApplicationResponseDTO toResponseDTO(JobApplication jobApplication);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "jobPost", ignore = true)
    JobApplication toEntity(JobApplicationRequestDTO requestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appliedAt", ignore = true)
    @Mapping(target = "jobPost", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "applicantId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void toEntity(JobApplicationUpdateDTO dto, @MappingTarget JobApplication entity);

    @Mapping(target = "applicant", ignore = true)
    JobApplicationForCompanyDTO toJobApplicationForCompanyDTO(JobApplication jobApplication);

    JobApplicationForUserDTO toJobApplicationForUserDTO(JobApplication jobApplication);

    @Mapping(source = "id", target = "applicationId")
    @Mapping(source = "jobPost.companyId", target = "companyId")
    @Mapping(source = "jobPost.id", target = "jobPostId")
    @Mapping(source = "jobPost.title", target = "jobTitle")
    JobApplicationEvent toEvent(JobApplication jobApplication);

}
