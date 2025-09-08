package com.ahad.dto.update;

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
public class AddressUpdateDTO {

    @Size(max = 30, message = "Path can be at most 30 characters long")
    private String path;

    @Size(max = 50, message = "Landmark can be at most 50 characters long")
    private String landmark;

    @Size(max = 30, message = "City can be at most 30 characters long")
    private String city;

    @Size(max = 30, message = "State can be at most 30 characters long")
    private String state;

    @Size(max = 30, message = "Country can be at most 30 characters long")
    private String country;

    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be a 6-digit number")
    private String pincode;
}
