package com.ahad.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.ahad.dto.exports.UserForCompanyDTO;
import com.ahad.dto.exports.UserForJobDTO;
import com.ahad.dto.profile.UserProfileDTO;
import com.ahad.dto.request.UserRequestDTO;
import com.ahad.dto.response.UserResponseDTO;
import com.ahad.dto.response.UserSearchDTO;
import com.ahad.dto.update.UserUpdateDTO;
import com.ahad.enums.UserRole;

public interface UserService {

        // Create a new user
        UserResponseDTO createUser(UserRequestDTO userRequestDTO);

        // Get single user by ID
        UserProfileDTO getUserProfileById(UUID id);

        // Update user by ID
        UserResponseDTO updateUser(UUID id, UserUpdateDTO userUpdateDTO);

        // Delete user by ID
        void deleteUser(UUID id);

        // Get all users by name with pagination + sorting
        Page<UserSearchDTO> getAllUsersByName(String name, int pageNumber, int pageSize, String sortBy,
                        String sortDir);

        // Get all users by role with pagination + sorting
        Page<UserSearchDTO> getAllUsersByRole(UserRole roleType, int pageNumber, int pageSize, String sortBy,
                        String sortDir);

        // Optionally: Get all users without filters
        List<UserProfileDTO> getAllUsers();

        // Client Service Calls
        List<UserForCompanyDTO> getAllUsersByCompanyId(UUID companyId);

        UserForCompanyDTO getUserForCompanyById(UUID id);

        UserForJobDTO getUserForJobById(UUID id);
}
