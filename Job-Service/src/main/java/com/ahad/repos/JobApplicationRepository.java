package com.ahad.repos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahad.models.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

    // ✅ JobApplication → JobPost → id
    List<JobApplication> findByJobPostId(UUID jobId);

    // ✅ JobApplication → applicantId
    List<JobApplication> findByApplicantId(UUID applicantId);

    // ✅ JobApplication → JobPost → companyId
    List<JobApplication> findByJobPostCompanyId(UUID companyId);
}
