package com.ahad.services.internal;

import com.ahad.dto.request.JobHistoryRequestDTO;
import com.ahad.dto.response.JobHistoryResponseDTO;
import com.ahad.dto.update.JobHistoryUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface JobHistoryService {

    JobHistoryResponseDTO addJobHistory(JobHistoryRequestDTO jobHistoryRequestDTO);

    JobHistoryResponseDTO getJobHistoryById(UUID id);

    List<JobHistoryResponseDTO> getAllJobHistoryByUserInformationId(UUID userInformationId);

    JobHistoryResponseDTO updateJobHistory(UUID id, JobHistoryUpdateDTO dto);

    void deleteJobHistory(UUID id);
}
