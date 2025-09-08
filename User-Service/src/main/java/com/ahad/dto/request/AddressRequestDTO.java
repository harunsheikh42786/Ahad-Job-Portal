package com.ahad.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDTO {

    @NotBlank(message = "Address path is required")
    @Size(max = 30, message = "Path can be at most 30 characters long")
    private String path;

    @Size(max = 50, message = "Landmark can be at most 50 characters long")
    private String landmark;

    @NotBlank(message = "City is required")
    @Size(max = 30, message = "City can be at most 30 characters long")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 30, message = "State can be at most 30 characters long")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 30, message = "Country can be at most 30 characters long")
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be a 6-digit number")
    private String pincode;

    @NotNull(message = "UserInformation ID is required")
    private String userInformationId; // <-- sirf ID bhejna, poora entity nahi
}
