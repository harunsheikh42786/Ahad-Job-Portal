package com.ahad.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "company_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAddress {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    private String state;

    private String country;

    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_info_id", nullable = false)
    private CompanyInformation companyInformation;
}
