package com.ahad.repos;

import com.ahad.models.JobHistory;
import com.ahad.models.User;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, UUID> {

    List<JobHistory> findByUserInformationId(UUID userInformationId);

    List<JobHistory> findByCompanyIdAndCurrentJobTrue(UUID companyId);

    List<JobHistory> findByCompanyId(UUID companyId);

    @Query("SELECT DISTINCT j.userInformation.user FROM JobHistory j WHERE j.companyId = :companyId")
    List<User> findUsersByCompanyId(@Param("companyId") UUID companyId);

}
