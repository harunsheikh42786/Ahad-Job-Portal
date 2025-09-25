package com.ahad.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ahad.dto.exports.UserForCompanyDTO;
import com.ahad.dto.exports.UserForJobDTO;
import com.ahad.dto.imports.JobPostForUserDTO;
import com.ahad.dto.profile.UserProfileDTO;
import com.ahad.dto.request.UserRequestDTO;
import com.ahad.dto.response.UserResponseDTO;
import com.ahad.dto.response.UserSearchDTO;
import com.ahad.dto.update.UserUpdateDTO;
import com.ahad.enums.SortBy;
import com.ahad.enums.UserRole;
import com.ahad.exceptions.DuplicateResourceException;
import com.ahad.exceptions.MappingFailedException;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.helper.ApiResponse;
import com.ahad.mapper.UserMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.JobHistory;
import com.ahad.models.User;
import com.ahad.models.UserInformation;
import com.ahad.repos.JobHistoryRepository;
import com.ahad.repos.UserRepository;
import com.ahad.services.external.JobService;
import com.ahad.services.internal.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final JobHistoryRepository jobHistoryRepository;
        private final JobService jobService;
        private final PasswordEncoder passwordEncoder;

        @Override
        public boolean verifyUser(String username, String password) {
                if (userRepository.existsByEmail(username)) {
                        User existingUser = userRepository.findByEmail(username); // ✅ Find by username/email
                        return passwordEncoder.matches(password, existingUser.getPassword()); // ✅ Correct password
                                                                                              // verification
                }
                return false;
        }

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
                user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
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
        public UserProfileDTO getUserProfileById(UUID id) {
                User fetchedUser = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.NOT_FOUND + id));
                UserProfileDTO profileDTO = Optional.ofNullable(userMapper.toProfileDTO(fetchedUser))
                                .orElseThrow(() -> new MappingFailedException(ResponseMessage.MAPPING_FAILED));
                ApiResponse<List<JobPostForUserDTO>> appliedJobs = jobService.getJobsByUserId(id);
                if (appliedJobs.isSuccess()) {
                        profileDTO.getUserInformation().setAppliedJobs(appliedJobs.getData());
                }
                return profileDTO;
        }

        @Override
        public UserResponseDTO updateUser(UUID userId, UserUpdateDTO dto) {
                User existingUser = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.ID_NOT_FOUND));

                // ✅ Agar userInformation null hai to naya banao
                if (existingUser.getUserInformation() == null) {
                        existingUser.setUserInformation(new UserInformation());
                }

                // ✅ DTO -> Entity mapping
                userMapper.toUserEntity(dto, existingUser);

                // ✅ Relation ensure karo
                if (existingUser.getUserInformation() != null) {
                        existingUser.getUserInformation().setUser(existingUser);
                }

                if (!dto.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
                }

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
        public List<UserForCompanyDTO> getAllUsersByCompanyId(UUID companyId) {
                List<JobHistory> jobHistories = jobHistoryRepository.findByCompanyIdAndCurrentJobTrue(companyId);

                // Map userId -> JobHistory (current job)
                Map<UUID, JobHistory> userJobMap = jobHistories.stream()
                                .collect(Collectors.toMap(
                                                j -> j.getUserInformation().getUser().getId(),
                                                j -> j,
                                                (existing, replacement) -> existing // in case of duplicate user, keep
                                                                                    // first
                                ));

                return userJobMap.values().stream()
                                .map(j -> {
                                        User u = j.getUserInformation().getUser();
                                        return new UserForCompanyDTO(
                                                        u.getId().toString(),
                                                        u.getFirstName(),
                                                        u.getLastName(),
                                                        j.getJobTitle(),
                                                        j.getStartDate());
                                })
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

        // Get User For Company By Id
        @Override
        public UserForCompanyDTO getUserForCompanyById(UUID id) {
                User fetchedUser = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.NOT_FOUND + id));
                return UserForCompanyDTO.builder()
                                .id(fetchedUser.getId().toString())
                                .firstName(fetchedUser.getFirstName())
                                .lastName(fetchedUser.getLastName())
                                .position(fetchedUser.getUserInformation().getJobHistories().stream()
                                                .filter(JobHistory::isCurrentJob)
                                                .findFirst()
                                                .map(JobHistory::getJobTitle)
                                                .orElse(null))
                                .startDate(fetchedUser.getUserInformation().getJobHistories().stream()
                                                .filter(JobHistory::isCurrentJob)
                                                .findFirst()
                                                .map(JobHistory::getStartDate)
                                                .orElse(null))
                                .build();
        }

        // Get User For Job By Id
        @Override
        public UserForJobDTO getUserForJobById(UUID id) {
                User fetchedUser = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User " + ResponseMessage.NOT_FOUND + id));
                return UserForJobDTO.builder()
                                .id(fetchedUser.getId().toString())
                                .firstName(fetchedUser.getFirstName())
                                .lastName(fetchedUser.getLastName())
                                .headline(fetchedUser.getUserInformation().getHeadline())
                                .build();
        }

}
