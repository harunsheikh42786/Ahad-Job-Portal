package com.ahad.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ahad.dto.CompanyProfileDTO;
import com.ahad.dto.CompanyRequestDTO;
import com.ahad.dto.CompanyResponseDTO;
import com.ahad.dto.CompanyUpdateDTO;
import com.ahad.exceptions.DuplicateResourceException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.CompanyMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Company;
import com.ahad.models.CompanyInformation;
import com.ahad.repos.CompanyRepository;
import com.ahad.services.CompanyService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

        private final CompanyRepository companyRepository;
        private final CompanyMapper companyMapper;

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

                CompanyInformation companyInformation = Optional
                                .ofNullable(companyMapper.maptoCompanyInformation(companyRequestDTO))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                // Save to DB
                company.setOpen(true);
                company.setTimeStamp(LocalDateTime.now());
                company.setCompanyInformation(companyInformation);
                companyInformation.setCompany(company);

                Company savedCompany = companyRepository.save(company);

                // Entity → ResponseDTO (with mapping check)
                return Optional.ofNullable(companyMapper.mapToCompanyResponseDTO(savedCompany))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // Get Company By Id
        @Override
        public CompanyProfileDTO getCompanyById(String id) {
                Company fetchedCompany = companyRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException("Company " +
                                                ResponseMessage.ID_NOT_FOUND + id));

                return Optional.ofNullable(companyMapper.mapToProfileDTO(fetchedCompany))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        @Override
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
}
