package com.ahad.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ahad.dto.JobPostProfileDTO;
import com.ahad.dto.JobPostRequestDTO;
import com.ahad.dto.JobPostResponseDTO;
import com.ahad.dto.JobPostSearchDTO;
import com.ahad.dto.JobPostUpdateDTO;
import com.ahad.models.JobPost;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    JobPost mapToJobPost(JobPostRequestDTO dto);

    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "companyName", ignore = true)
    JobPostResponseDTO mapToJobPostResponseDTO(JobPost jobPost);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "postedAt", ignore = true)
    @Mapping(target = "recruiterId", ignore = true)
    void toEntity(JobPostUpdateDTO dto, @MappingTarget JobPost entity);

    JobPostProfileDTO mapToProfileDTO(JobPost jobPost);

    @Mapping(target = "companyName", ignore = true)
    JobPostSearchDTO mapToSearchDTO(JobPost jobPost);

    List<JobPostSearchDTO> mapToSearchDTOs(List<JobPost> jobPost);

    List<JobPostResponseDTO> mapToResponseDTOs(List<JobPost> jobPosts);

}