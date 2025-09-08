package com.ahad.services;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.CompanyProfileDTO;
import com.ahad.dto.CompanyRequestDTO;
import com.ahad.dto.CompanyResponseDTO;
import com.ahad.dto.CompanyUpdateDTO;

public interface CompanyService {

        // Create a new company
        CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);

        // Get single company by ID
        CompanyProfileDTO getCompanyById(String id);

        // Update existing company
        CompanyResponseDTO updateCompany(UUID id, CompanyUpdateDTO companyUpdateDTO);

        // Delete company by ID
        void deleteCompany(UUID id);

        // // // Get all companys by name with pagination + sorting
        // // Page<CompanySearchDTO> getAllCompanysByName(String name, int pageNumber,
        // int
        // // pageSize, String sortBy,
        // // String sortDir);

        // Optionally: Get all companys without filters
        List<CompanyResponseDTO> getAllCompanies();
}
