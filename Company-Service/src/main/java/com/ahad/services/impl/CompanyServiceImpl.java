package com.ahad.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.ahad.dto.exports.CompanySearchDTO;
import com.ahad.dto.imports.JobPostForCompanyDTO;
import com.ahad.dto.imports.UserSearchForCompanyhDTO;
import com.ahad.dto.profile.CompanyProfileDTO;
import com.ahad.dto.request.CompanyRequestDTO;
import com.ahad.dto.response.CompanyResponseDTO;
import com.ahad.dto.update.CompanyUpdateDTO;
import com.ahad.exceptions.DuplicateResourceException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.helper.ApiResponse;
import com.ahad.mapper.CompanyMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Company;
import com.ahad.repos.CompanyRepository;
import com.ahad.services.external.JobService;
import com.ahad.services.external.UserService;
import com.ahad.services.internal.CompanyService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

        private final CompanyRepository companyRepository;
        private final CompanyMapper companyMapper;
        private final UserService userService;
        private final JobService jobService;

        // Register new Company
        @Override
        public CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO) {
                if (companyRepository.existsByEmail(companyRequestDTO.getEmail())) {
                        throw new DuplicateResourceException("Company " +
                                        ResponseMessage.DUPLICATE_EMAIL +
                                        companyRequestDTO.getEmail());
                }
                if (companyRepository.existsByContactNumber(companyRequestDTO.getContactNumber())) {
                        throw new DuplicateResourceException("Company " +
                                        ResponseMessage.DUPLICATE_CONTACT_NUMBER +
                                        companyRequestDTO.getContactNumber());
                }

                // RequestDTO → Entity (with mapping check)
                Company company = Optional.ofNullable(companyMapper.maptoCompany(companyRequestDTO))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                // Save to DB
                company.setOpen(true);
                company.setTimeStamp(LocalDateTime.now());

                Company savedCompany = companyRepository.save(company);

                // Entity → ResponseDTO (with mapping check)
                return Optional.ofNullable(companyMapper.mapToCompanyResponseDTO(savedCompany))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // Get Company By Id
        @Override
        public CompanyProfileDTO getCompanyById(String id) {
                // 1️⃣ Fetch company entity
                Company fetchedCompany = companyRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Company " + ResponseMessage.ID_NOT_FOUND + id));

                // 2️⃣ Map to DTO
                CompanyProfileDTO companyProfileDTO = companyMapper.mapToProfileDTO(fetchedCompany);

                // 3️⃣ Fetch users via Feign client
                ApiResponse<List<UserSearchForCompanyhDTO>> apiResponse = userService
                                .getUsersByCompanyId(UUID.fromString(id));

                // 4️⃣ Attach employers list to DTO
                if (apiResponse.isSuccess()) {
                        companyProfileDTO.getCompanyInformationDTO().setEmployers(apiResponse.getData());
                }
                // 5️⃣ Fetch jobs via Feign client
                ApiResponse<List<JobPostForCompanyDTO>> jobApiResponse = jobService
                                .getJobsByCompanyId(UUID.fromString(id));

                if (jobApiResponse.isSuccess()) {
                        companyProfileDTO.getCompanyInformationDTO().setJobPosts(jobApiResponse.getData());
                }

                return companyProfileDTO;
        }

        public CompanyResponseDTO updateCompany(UUID companyId, CompanyUpdateDTO dto) {
                Company existingCompany = companyRepository.findById(companyId)
                                .orElseThrow(() -> new ResourceNotFoundException("Company " +
                                                ResponseMessage.ID_NOT_FOUND + companyId.toString()));

                companyMapper.toEntity(dto, existingCompany);

                // TODO: apply updates from dto → existingCompany if required
                Company savedCompany = companyRepository.save(existingCompany);

                return Optional.ofNullable(companyMapper.mapToCompanyResponseDTO(savedCompany))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // DeleteCompany By Id
        @Override
        public void deleteCompany(UUID id) {
                Company company = companyRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Company " +
                                                ResponseMessage.ID_NOT_FOUND + id.toString()));

                companyRepository.delete(company);
        }

        @Override
        public List<CompanyResponseDTO> getAllCompanies() {
                List<Company> listOfCompanies = this.companyRepository.findAll();
                return listOfCompanies.stream().map(companyMapper::mapToCompanyResponseDTO).toList();
        }

        @Override
        public CompanySearchDTO getCompanyByIdForClient(String id) {
                Company fetchedCompany = companyRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Company " + ResponseMessage.ID_NOT_FOUND + id));

                return new CompanySearchDTO(fetchedCompany.getId(), fetchedCompany.getName(),
                                fetchedCompany.getCompanyInformation().getHeadline(),
                                fetchedCompany.getCompanyInformation().getAddresses().get(0).getCity());

                // return
                // Optional.ofNullable(companyMapper.mapToCompanySearchDTO(fetchedCompany))
                // .orElseThrow(() -> new
                // MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }
}
