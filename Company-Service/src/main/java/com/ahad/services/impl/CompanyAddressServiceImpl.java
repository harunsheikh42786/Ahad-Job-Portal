package com.ahad.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ahad.dto.profile.CompanyAddressProfileDTO;
import com.ahad.dto.request.CompanyAddressRequestDTO;
import com.ahad.dto.response.CompanyAddressResponseDTO;
import com.ahad.dto.update.CompanyAddressUpdateDTO;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.CompanyAddressMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.CompanyAddress;
import com.ahad.models.CompanyInformation;
import com.ahad.repos.CompanyAddressRepository;
import com.ahad.repos.CompanyInformationRepository;
import com.ahad.services.internal.CompanyAddressService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyAddressServiceImpl implements CompanyAddressService {

        private final CompanyAddressRepository companyAddressRepository;
        private final CompanyInformationRepository companyInformationRepository;
        private final CompanyAddressMapper companyAddressMapper;

        // ✅ Create Address
        @Override
        public CompanyAddressResponseDTO createCompanyAddress(UUID companyInfoId, CompanyAddressRequestDTO requestDTO) {
                CompanyInformation companyInfo = companyInformationRepository.findById(companyInfoId)
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyInformation " +
                                                ResponseMessage.ID_NOT_FOUND + companyInfoId));

                CompanyAddress companyAddress = Optional
                                .ofNullable(companyAddressMapper.requestToEntity(requestDTO))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                companyAddress.setCompanyInformation(companyInfo);
                CompanyAddress saved = companyAddressRepository.save(companyAddress);

                return Optional.ofNullable(companyAddressMapper.entityToResponse(saved))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // ✅ Get Address by Id
        @Override
        public CompanyAddressProfileDTO getCompanyAddressById(String id) {
                CompanyAddress address = companyAddressRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyAddress " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                return companyAddressMapper.entityToProfile(address);
        }

        // ✅ Update Address
        @Override
        public CompanyAddressResponseDTO updateCompanyAddress(UUID id, CompanyAddressUpdateDTO updateDTO) {
                CompanyAddress existing = companyAddressRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyAddress " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                companyAddressMapper.toEntity(updateDTO, existing);

                CompanyAddress saved = companyAddressRepository.save(existing);

                return Optional.ofNullable(companyAddressMapper.entityToResponse(saved))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // ✅ Delete Address
        @Override
        public void deleteCompanyAddress(UUID id) {
                CompanyAddress address = companyAddressRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("CompanyAddress " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                companyAddressRepository.delete(address);
        }

        // ✅ Get All Addresses
        @Override
        public List<CompanyAddressResponseDTO> getAllCompanyAddresses() {
                List<CompanyAddress> addresses = companyAddressRepository.findAll();
                return addresses.stream()
                                .map(companyAddressMapper::entityToResponse)
                                .toList();
        }
}
