package com.ahad.servicesImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ahad.dto.UserSearchDTO;
import com.ahad.dto.profile.UserProfileDTO;
import com.ahad.dto.request.UserRequestDTO;
import com.ahad.dto.response.UserResponseDTO;
import com.ahad.dto.update.UserUpdateDTO;
import com.ahad.enums.Gender;
import com.ahad.enums.SortBy;
import com.ahad.enums.UserRole;
import com.ahad.exceptions.DuplicateResourceException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.UserMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.User;
import com.ahad.repos.UserRepository;
import com.ahad.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        // private final PasswordEncoder passwordEncoder;

        // Register new User
        @Override
        public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
                if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
                        throw new DuplicateResourceException("User " +
                                        ResponseMessage.DUPLICATE_EMAIL + userRequestDTO.getEmail());
                }
                if (userRepository.existsByContactNumber(userRequestDTO.getContactNumber())) {
                        throw new DuplicateResourceException("User " +
                                        ResponseMessage.DUPLICATE_CONTACT_NUMBER + userRequestDTO.getContactNumber());
                }

                // RequestDTO → Entity (with mapping check)
                User user = Optional.ofNullable(userMapper.toUserEntity(userRequestDTO))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));

                // ModelMapper mapper = new ModelMapper(); // map at runtime
                // User user = mapper.map(userRequestDTO, User.class);
                // UserResponseDTO response = mapper.map(user, UserResponseDTO.class);

                // Save to DB
                user.setActive(true);
                user.setTimeStamp(LocalDateTime.now());
                user.setRole(user.getRole() == null ? UserRole.JOB_SEEKER : user.getRole());
                User savedUser = userRepository.save(user);

                // Entity → ResponseDTO (with mapping check)
                return Optional.ofNullable(userMapper.toResponseDto(savedUser))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // Get User By Id
        @Override
        public UserProfileDTO getUserById(String id) {
                User fetchedUser = userRepository.findById(UUID.fromString(id))
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.NOT_FOUND + id));
                return Optional.ofNullable(userMapper.toProfileDTO(fetchedUser))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        @Override
        public UserResponseDTO updateUser(UUID userId, UserUpdateDTO dto) {
                User existingUser = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.ID_NOT_FOUND));

                userMapper.toUserEntity(dto, existingUser);

                User savedUser = userRepository.save(existingUser);
                return Optional.ofNullable(userMapper.toResponseDto(savedUser))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
        }

        // DeleteUser By Id
        @Override
        public void deleteUser(UUID id) {
                User user = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.ID_NOT_FOUND + id));

                userRepository.delete(user);
        }

        // @Override
        // public List<User> getAllUsersByName(String userName) {
        // // Example: fetch all users sorted by firstName, lastName
        // return userRepository.findAll(Sort.by(Sort.Direction.ASC, userName,
        // "lastName"));
        // }

        @Override
        public List<UserProfileDTO> getAllUsers() {
                return this.userRepository.findAll().stream().map(userMapper::toProfileDTO)
                                .collect(Collectors.toList());
        }

        @Override
        public Page<UserSearchDTO> getAllUsersByRole(UserRole roleType, int pageNumber, int pageSize, String sortBy,
                        String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase(SortBy.ASCENDING.toString())
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

                Page<User> users = userRepository.findByRole(roleType, pageable);

                // 👇 MapStruct से DTO में convert करना
                return users.map(userMapper::toSearchDTO);
        }

        @Override
        public Page<UserSearchDTO> getAllUsersByName(String name, int pageNumber, int pageSize, String sortBy,
                        String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase(SortBy.ASCENDING.toString())
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

                Page<User> users = userRepository.searchByName(name, pageable);

                return users.map(userMapper::toSearchDTO);
        }

}
