package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.exports.CompanySearchDTO;
import com.ahad.dto.profile.CompanyProfileDTO;
import com.ahad.dto.request.CompanyRequestDTO;
import com.ahad.dto.response.CompanyResponseDTO;
import com.ahad.dto.update.CompanyUpdateDTO;

public interface CompanyService {

        boolean verifyCompany(String username, String password);

        boolean existsByEmail(String username);

        // Create a new company
        CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);

        // Get single company by ID
        CompanyProfileDTO getCompanyById(String id);

        CompanySearchDTO getCompanyByIdForClient(String id);

        // Update existing company
        CompanyResponseDTO updateCompany(UUID id, CompanyUpdateDTO companyUpdateDTO);

        // Delete company by ID
        void deleteCompany(UUID id);

        List<CompanyResponseDTO> getAllCompanies();
}
