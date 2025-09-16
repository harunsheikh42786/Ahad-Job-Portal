package com.ahad.controllers;

import com.ahad.dto.exports.UserForCompanyDTO;
import com.ahad.dto.exports.UserForJobDTO;
import com.ahad.dto.profile.UserProfileDTO;
import com.ahad.dto.response.UserResponseDTO;
import com.ahad.dto.response.UserSearchDTO;
import com.ahad.dto.update.UserUpdateDTO;
import com.ahad.enums.UserRole;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.UserService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiVersion.V1 + "/users") // plural REST best practice
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

        // ✅ Get user by ID
        @GetMapping("/profile/{id}")
        public ResponseEntity<ApiResponse<UserProfileDTO>> getUserProfileById(@PathVariable UUID id) {
                UserProfileDTO userProfileDTO = userService.getUserProfileById(id);
                ApiResponse<UserProfileDTO> apiResponse = ApiResponse.<UserProfileDTO>builder()
                                .success(true)
                                .message("User " + ResponseMessage.FETCHED)
                                .data(userProfileDTO)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Update user
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
                        @PathVariable UUID id,
                        @RequestBody UserUpdateDTO dto) {

                UserResponseDTO updatedUser = userService.updateUser(id, dto);

                ApiResponse<UserResponseDTO> response = ApiResponse.<UserResponseDTO>builder()
                                .success(true)
                                .message("User " + ResponseMessage.UPDATED)
                                .data(updatedUser)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Delete user
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Boolean>> deleteUser(@PathVariable UUID id) {
                userService.deleteUser(id);
                ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                                .success(true)
                                .data(true)
                                .status(HttpStatus.OK.value())
                                .message("User " + ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Get all users
        @GetMapping
        public ResponseEntity<ApiResponse<List<UserProfileDTO>>> getAllUsers() {
                List<UserProfileDTO> users = userService.getAllUsers();

                ApiResponse<List<UserProfileDTO>> apiResponse = ApiResponse.<List<UserProfileDTO>>builder()
                                .success(true)
                                .data(users)
                                .status(HttpStatus.OK.value())
                                .message("User " + (users.isEmpty()
                                                ? ResponseMessage.NOT_FOUND
                                                : ResponseMessage.FETCHED))
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Fetch users by role
        @GetMapping("/search/by-role")
        public ResponseEntity<ApiResponse<Page<UserSearchDTO>>> getUsersByRole(
                        @RequestParam UserRole role,
                        @RequestParam(defaultValue = "0") int pageNumber,
                        @RequestParam(defaultValue = "10") int pageSize,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String sortDir) {

                Page<UserSearchDTO> users = userService.getAllUsersByRole(role, pageNumber, pageSize, sortBy, sortDir);

                ApiResponse<Page<UserSearchDTO>> response = ApiResponse.<Page<UserSearchDTO>>builder()
                                .success(users.isEmpty() ? true : false)
                                .data(users)
                                .status(HttpStatus.OK.value())
                                .message("User " + (users.isEmpty()
                                                ? ResponseMessage.NOT_FOUND
                                                : ResponseMessage.FETCHED))
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Fetch users by name
        @GetMapping("/search/by-name")
        public ResponseEntity<ApiResponse<Page<UserSearchDTO>>> getUsersByName(
                        @RequestParam String name,
                        @RequestParam(defaultValue = "0") int pageNumber,
                        @RequestParam(defaultValue = "10") int pageSize,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String sortDir) {

                Page<UserSearchDTO> users = userService.getAllUsersByName(name, pageNumber, pageSize, sortBy, sortDir);

                ApiResponse<Page<UserSearchDTO>> response = ApiResponse.<Page<UserSearchDTO>>builder()
                                .success(true)
                                .data(users)
                                .status(HttpStatus.OK.value())
                                .message("User " + (users.isEmpty()
                                                ? ResponseMessage.NOT_FOUND
                                                : ResponseMessage.FETCHED))
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.ok(response);
        }

        // ✅ Fetch users by company ID
        @GetMapping("/company/all/{companyId}")
        public ResponseEntity<ApiResponse<List<UserForCompanyDTO>>> getAllUsersByCompanyId(
                        @PathVariable UUID companyId) {
                List<UserForCompanyDTO> employers = userService.getAllUsersByCompanyId(companyId);

                ApiResponse<List<UserForCompanyDTO>> apiResponse = ApiResponse.<List<UserForCompanyDTO>>builder()
                                .success(true)
                                .data(employers)
                                .status(HttpStatus.OK.value())
                                .message("Employers " + (employers.isEmpty()
                                                ? ResponseMessage.NOT_FOUND
                                                : ResponseMessage.FETCHED))
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Get user by ID for Company Service
        @GetMapping("/company/{id}")
        public ResponseEntity<ApiResponse<UserForCompanyDTO>> getUserForCompanyById(@PathVariable UUID id) {
                UserForCompanyDTO userForCompanyDTO = userService.getUserForCompanyById(id);
                ApiResponse<UserForCompanyDTO> apiResponse = ApiResponse.<UserForCompanyDTO>builder()
                                .success(true)
                                .message("User " + ResponseMessage.FETCHED)
                                .data(userForCompanyDTO)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(apiResponse);
        }

        // ✅ Get user by ID for Job Service
        @GetMapping("/job/{id}")
        public ResponseEntity<ApiResponse<UserForJobDTO>> getUserForJobById(@PathVariable UUID id) {
                UserForJobDTO userForJobDTO = userService.getUserForJobById(id);
                ApiResponse<UserForJobDTO> apiResponse = ApiResponse.<UserForJobDTO>builder()
                                .success(true)
                                .message("User " + ResponseMessage.FETCHED)
                                .data(userForJobDTO)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(apiResponse);
        }
}
