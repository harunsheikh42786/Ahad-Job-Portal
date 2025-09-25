package com.ahad.services.impl;

import com.ahad.dto.request.AddressRequestDTO;
import com.ahad.dto.response.AddressResponseDTO;
import com.ahad.dto.update.AddressUpdateDTO;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.AddressMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.Address;
import com.ahad.models.UserInformation;
import com.ahad.repos.AddressRepository;
import com.ahad.repos.UserInformationRepository;
import com.ahad.services.internal.AddressService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserInformationRepository userInformationRepository;

    @Override
    public AddressResponseDTO createAddress(AddressRequestDTO dto) {
        UUID userInfoId = UUID.fromString(dto.getUserInformationId());
        UserInformation fethedUserInformation = userInformationRepository.findById(userInfoId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "UserInformation " + ResponseMessage.ID_NOT_FOUND + userInfoId.toString()));

        Address address = addressMapper.toEntity(dto);
        address.setUserInformation(fethedUserInformation);

        Address saved = addressRepository.save(address);
        return addressMapper.toResponseDTO(saved);
    }

    @Override
    public AddressResponseDTO getAddressById(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Addess " + ResponseMessage.ID_NOT_FOUND + id));
        return addressMapper.toResponseDTO(address);
    }

    @Override
    public AddressResponseDTO getAddressByUserInformationId(UUID userInformationId) {
        UserInformation userInformation = userInformationRepository.findById(userInformationId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "UserInformation " + ResponseMessage.ID_NOT_FOUND + userInformationId));

        // Address address = addressRepository.findById(id)
        // .orElseThrow(() -> new EntityNotFoundException("Addess " +
        // ResponseMessage.ID_NOT_FOUND + id));
        return addressMapper.toResponseDTO(userInformation.getAddress());
    }

    @Override
    public AddressResponseDTO updateAddress(UUID id, AddressUpdateDTO dto) {
        // Fetch existing address
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Addess " + ResponseMessage.ID_NOT_FOUND + id));

        addressMapper.updateEntityFromDto(dto, address);

        // Save updated entity
        Address updated = addressRepository.save(address);

        // Convert to Response DTO
        return addressMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteAddress(UUID id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Addess " + ResponseMessage.ID_NOT_FOUND + id));
        addressRepository.delete(address);
    }

}
