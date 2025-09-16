package com.ahad.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ahad.dto.profile.CompanyInformationProfileDTO;
import com.ahad.dto.request.CompanyInformationRequestDTO;
import com.ahad.dto.response.CompanyInformationResponseDTO;
import com.ahad.dto.update.CompanyInformationUpdateDTO;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.CompanyInformationMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Company;
import com.ahad.models.CompanyInformation;
import com.ahad.repos.CompanyInformationRepository;
import com.ahad.repos.CompanyRepository;
import com.ahad.services.internal.CompanyInformationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyInformationServiceImpl implements CompanyInformationService {

        private final CompanyRepository companyRepository;
        private final CompanyInformationRepository companyInformationRepository;
        private final CompanyInformationMapper companyInformationMapper;

        // ✅ Create CompanyInformation
        @Override
        public CompanyInformationResponseDTO createCompanyInformation(UUID companyId,
                        CompanyInformationRequestDTO requestDTO) {
                Company company = companyRepository.findById(companyId)
                                .orElseThrow(() -> new ResourceNotFoundException("Company " +
                                                ResponseMessage.ID_NOT_FOUND + companyId));

                // RequestDTO → Entity
                CompanyInformation companyInformation = Optional
                                .ofNullable(companyInformationMapper.requestToEntity(requestDTO))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                companyInformation.setCompany(company);
                company.setCompanyInformation(companyInformation);

                CompanyInformation savedInfo = companyInformationRepository.save(companyInformation);

                // Entity → ResponseDTO
                return Optional.ofNullable(companyInformationMapper.entityToResponse(savedInfo))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // ✅ Get CompanyInformation By Id
        @Override
        public CompanyInformationProfileDTO getCompanyInformationById(String id) {
                CompanyInformation info = companyInformationRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyInformation " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                return companyInformationMapper.entityToProfile(info);
        }

        // ✅ Update CompanyInformation
        @Override
        public CompanyInformationResponseDTO updateCompanyInformation(UUID id,
                        CompanyInformationUpdateDTO updateDTO) {
                CompanyInformation existingInfo = companyInformationRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyInformation " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                // DTO → update existing entity
                companyInformationMapper.toEntity(updateDTO, existingInfo);

                CompanyInformation savedInfo = companyInformationRepository.save(existingInfo);

                return Optional.ofNullable(companyInformationMapper.entityToResponse(savedInfo))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // ✅ Delete CompanyInformation
        @Override
        public void deleteCompanyInformation(UUID id) {
                CompanyInformation info = companyInformationRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyInformation " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                companyInformationRepository.delete(info);
        }

        // ✅ Get All CompanyInformation
        @Override
        public List<CompanyInformationResponseDTO> getAllCompanies() {
                List<CompanyInformation> infoList = companyInformationRepository.findAll();
                return infoList.stream()
                                .map(companyInformationMapper::entityToResponse)
                                .toList();
        }
}
