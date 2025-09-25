package com.ahad.controllers;

import com.ahad.dto.request.AddressRequestDTO;
import com.ahad.dto.response.AddressResponseDTO;
import com.ahad.dto.update.AddressUpdateDTO;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;
import com.ahad.messages.ResponseMessage;
import com.ahad.services.internal.AddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(ApiVersion.V1 + "/addresses")
@RequiredArgsConstructor
public class AddressController {

        private final AddressService addressService;

        @PostMapping
        public ResponseEntity<ApiResponse<AddressResponseDTO>> createAddress(@RequestBody AddressRequestDTO dto) {
                AddressResponseDTO created = addressService.createAddress(dto);
                ApiResponse<AddressResponseDTO> response = ApiResponse.<AddressResponseDTO>builder()
                                .success(true)
                                .message("Addess " + ResponseMessage.CREATED)
                                .data(created)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CREATED.value())
                                .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<AddressResponseDTO>> getAddressById(@PathVariable UUID id) {
                AddressResponseDTO address = addressService.getAddressById(id);
                ApiResponse<AddressResponseDTO> response = ApiResponse.<AddressResponseDTO>builder()
                                .success(true)
                                .message("Addess "
                                                + ResponseMessage.FETCHED)
                                .data(address)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<AddressResponseDTO>> updateAddress(
                        @PathVariable UUID id,
                        @RequestBody AddressUpdateDTO dto) {
                AddressResponseDTO updated = addressService.updateAddress(id, dto);
                ApiResponse<AddressResponseDTO> response = ApiResponse.<AddressResponseDTO>builder()
                                .success(true)
                                .message("Addess "
                                                + ResponseMessage.UPDATED)
                                .data(updated)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable UUID id) {
                addressService.deleteAddress(id);
                ApiResponse<Void> response = ApiResponse.<Void>builder()
                                .success(true)
                                .message("Addess "
                                                + ResponseMessage.DELETED)
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.OK.value())
                                .build();
                return ResponseEntity.ok(response);
        }
}
