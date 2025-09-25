package com.ahad.services.internal;

import com.ahad.dto.request.UserInformationRequestDTO;
import com.ahad.dto.response.UserInformationResponseDTO;
import com.ahad.dto.update.UserInformationUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UserInformationService {

    UserInformationResponseDTO createUserInformation(UserInformationRequestDTO dto);

    UserInformationResponseDTO getUserInformationById(UUID id);

    UserInformationResponseDTO getUserInformationByUserId(UUID id);

    List<UserInformationResponseDTO> getAllUserInformation();

    UserInformationResponseDTO updateUserInformation(UUID id, UserInformationUpdateDTO dto);

    void deleteUserInformation(UUID id);
}
