package com.ahad.repos;

import com.ahad.models.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, UUID> {
    List<Education> findByUserInformationId(UUID userInformationId);
}
