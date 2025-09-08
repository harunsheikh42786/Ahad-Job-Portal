package com.ahad.models;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInformation {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String websiteLink;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate registrationDate;

    @Column(nullable = false, unique = true, length = 13)
    private String registrationId;

    private String headline;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CompanyAddress> addresses;

    @Size(max = 2000)
    private String description;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    private Company company;

}
