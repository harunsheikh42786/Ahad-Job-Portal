package com.ahad.servicesImpl;

import com.ahad.dto.request.UserInformationRequestDTO;
import com.ahad.dto.response.UserInformationResponseDTO;
import com.ahad.dto.update.UserInformationUpdateDTO;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.UserInformationMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.User;
import com.ahad.models.UserInformation;
import com.ahad.repos.UserInformationRepository;
import com.ahad.repos.UserRepository;
import com.ahad.services.UserInformationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository userInformationRepository;
    private final UserInformationMapper userInformationMapper;
    private final UserRepository userRepository;

    @Override
    public UserInformationResponseDTO createUserInformation(UserInformationRequestDTO dto) {

        User fetchedUser = userRepository.findById(UUID.fromString(dto.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + ResponseMessage.ID_NOT_FOUND + dto.getUserId()));

        UserInformation userInfo = userInformationMapper.toEntity(dto);
        userInfo.setUser(fetchedUser);

        UserInformation saved = userInformationRepository.save(userInfo);
        return mapToResponseDTO(saved);
    }

    @Override
    public UserInformationResponseDTO getUserInformationById(UUID id) {
        UserInformation userInfo = userInformationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation " + ResponseMessage.ID_NOT_FOUND + id));
        return mapToResponseDTO(userInfo);
    }

    @Override
    public UserInformationResponseDTO getUserInformationByUserId(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User " + ResponseMessage.ID_NOT_FOUND + id));
        if (user.getUserInformation() == null)
            throw new ResourceNotFoundException("UserInformation " + ResponseMessage.ID_NOT_FOUND + id);
        return mapToResponseDTO(user.getUserInformation());
    }

    @Override
    public List<UserInformationResponseDTO> getAllUserInformation() {
        return userInformationRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserInformationResponseDTO updateUserInformation(UUID id, UserInformationUpdateDTO dto) {
        UserInformation userInfo = userInformationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserInformation " + ResponseMessage.ID_NOT_FOUND + id));

        userInformationMapper.updateEntityFromDto(dto, userInfo); // ✅ null-safe update
        UserInformation updated = userInformationRepository.save(userInfo);

        return userInformationMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteUserInformation(UUID id) {
        UserInformation userInfo = userInformationRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "UserInformation " + ResponseMessage.ID_NOT_FOUND + id));
        userInformationRepository.delete(userInfo);
    }

    private UserInformationResponseDTO mapToResponseDTO(UserInformation userInfo) {
        return UserInformationResponseDTO.builder()
                .id(userInfo.getId())
                .headline(userInfo.getHeadline())
                .portfolio(userInfo.getPortfolio())
                .jobStatus(userInfo.getJobStatus())
                .build();
    }
}
