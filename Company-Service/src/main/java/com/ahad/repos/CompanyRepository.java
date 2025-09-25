package com.ahad.repos;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahad.models.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    // @EntityGraph(attributePaths = { "companyInformation"})
    // Optional<Company> findById(UUID id);

    Company findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByContactNumber(String contactNumber);

    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
