package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.profile.CompanyInformationProfileDTO;
import com.ahad.dto.request.CompanyInformationRequestDTO;
import com.ahad.dto.response.CompanyInformationResponseDTO;
import com.ahad.dto.update.CompanyInformationUpdateDTO;

public interface CompanyInformationService {
        // Create a new companyinformation
        CompanyInformationResponseDTO createCompanyInformation(UUID companyId,
                        CompanyInformationRequestDTO companyinformationRequestDTO);

        // Get single companyinformation by ID
        CompanyInformationProfileDTO getCompanyInformationById(String id);

        // Update existing companyinformation
        CompanyInformationResponseDTO updateCompanyInformation(UUID id,
                        CompanyInformationUpdateDTO companyinformationUpdateDTO);

        // Delete companyinformation by ID
        void deleteCompanyInformation(UUID id);

        // // // Get all companyinformations by name with pagination + sorting
        // // Page<CompanyInformationSearchDTO> getAllCompanyInformationsByName(String
        // name, int pageNumber,
        // int
        // // pageSize, String sortBy,
        // // String sortDir);

        // Optionally: Get all companyinformations without filters
        List<CompanyInformationResponseDTO> getAllCompanies();

}
