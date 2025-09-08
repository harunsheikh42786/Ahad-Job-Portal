package com.ahad.models;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "Address path is required")
    @Size(max = 30, message = "Path can be at most 30 characters long")
    @Column(nullable = false, length = 30)
    private String path;

    @Size(max = 50, message = "Landmark can be at most 50 characters long")
    private String landmark;

    @NotBlank(message = "City is required")
    @Size(max = 30, message = "City can be at most 30 characters long")
    @Column(nullable = false, length = 30)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 30, message = "State can be at most 30 characters long")
    @Column(nullable = false, length = 30)
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 30, message = "Country can be at most 30 characters long")
    @Column(nullable = false, length = 30)
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be a 6-digit number")
    @Column(nullable = false, length = 6)
    private String pincode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_information_id", nullable = false)
    private UserInformation userInformation;
}
