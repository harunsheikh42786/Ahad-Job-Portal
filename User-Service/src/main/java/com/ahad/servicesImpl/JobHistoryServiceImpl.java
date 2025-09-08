package com.ahad.servicesImpl;

import com.ahad.dto.request.JobHistoryRequestDTO;
import com.ahad.dto.response.JobHistoryResponseDTO;
import com.ahad.dto.update.JobHistoryUpdateDTO;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.mapper.JobHistoryMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.JobHistory;
import com.ahad.models.UserInformation;
import com.ahad.repos.JobHistoryRepository;
import com.ahad.repos.UserInformationRepository;
import com.ahad.services.JobHistoryService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;
    private final JobHistoryMapper jobHistoryMapper;

    private final UserInformationRepository userInformationRepository;

    @Override
    public JobHistoryResponseDTO addJobHistory(JobHistoryRequestDTO jobHistoryRequestDTO) {
        UserInformation userInformation = userInformationRepository
                .findById(jobHistoryRequestDTO.getUserInformationId())
                .orElseThrow(() -> new ResourceNotFoundException("UserInformation " + ResponseMessage.ID_NOT_FOUND
                        + jobHistoryRequestDTO.getUserInformationId()));

        JobHistory jobHistory = jobHistoryMapper.toEntity(jobHistoryRequestDTO);

        jobHistory.setUserInformation(userInformation);
        JobHistory saved = jobHistoryRepository.save(jobHistory);
        return jobHistoryMapper.toDto(saved);
    }

    @Override
    public JobHistoryResponseDTO getJobHistoryById(UUID id) {
        JobHistory jobHistory = jobHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobHistory " +
                        ResponseMessage.ID_NOT_FOUND + id));
        return jobHistoryMapper.toDto(jobHistory);
    }

    @Override
    public List<JobHistoryResponseDTO> getAllJobHistoryByUserInformationId(UUID userInformationId) {
        return jobHistoryRepository.findByUserInformationId(userInformationId)
                .stream()
                .map(jobHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobHistoryResponseDTO updateJobHistory(UUID id, JobHistoryUpdateDTO dto) {
        JobHistory jobHistory = jobHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobHistory " +
                        ResponseMessage.ID_NOT_FOUND + id));

        jobHistoryMapper.updateEntityFromDto(dto, jobHistory);

        JobHistory updated = jobHistoryRepository.save(jobHistory);
        return jobHistoryMapper.toDto(updated);
    }

    @Override
    public void deleteJobHistory(UUID id) {
        JobHistory jobHistory = jobHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobHistory " +
                        ResponseMessage.ID_NOT_FOUND + id));
        jobHistoryRepository.delete(jobHistory);
    }

}
