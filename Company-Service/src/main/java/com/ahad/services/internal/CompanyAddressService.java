package com.ahad.services.internal;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.profile.CompanyAddressProfileDTO;
import com.ahad.dto.request.CompanyAddressRequestDTO;
import com.ahad.dto.response.CompanyAddressResponseDTO;
import com.ahad.dto.update.CompanyAddressUpdateDTO;

public interface CompanyAddressService {

    CompanyAddressResponseDTO createCompanyAddress(UUID companyInfoId, CompanyAddressRequestDTO requestDTO);

    CompanyAddressProfileDTO getCompanyAddressById(String id);

    CompanyAddressResponseDTO updateCompanyAddress(UUID id, CompanyAddressUpdateDTO updateDTO);

    void deleteCompanyAddress(UUID id);

    List<CompanyAddressResponseDTO> getAllCompanyAddresses();
}
