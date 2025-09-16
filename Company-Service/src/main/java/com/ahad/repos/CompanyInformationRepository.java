package com.ahad.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahad.models.CompanyInformation;

public interface CompanyInformationRepository extends JpaRepository<CompanyInformation, UUID> {

}
