package com.ahad.repos;

import com.ahad.models.JobHistory;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, UUID> {

    List<JobHistory> findByUserInformationId(UUID userInformationId);

}
