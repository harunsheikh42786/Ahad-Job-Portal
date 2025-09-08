package com.ahad.services;

import com.ahad.dto.request.AddressRequestDTO;
import com.ahad.dto.response.AddressResponseDTO;
import com.ahad.dto.update.AddressUpdateDTO;

import java.util.UUID;

public interface AddressService {

    AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO getAddressById(UUID addressId);

    AddressResponseDTO getAddressByUserInformationId(UUID userInformationId);

    AddressResponseDTO updateAddress(UUID addressId, AddressUpdateDTO addressUpdateDTO);

    void deleteAddress(UUID addressId);
}
