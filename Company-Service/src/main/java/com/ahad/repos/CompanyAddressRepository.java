package com.ahad.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahad.models.CompanyAddress;

public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, UUID> {

}
