package com.ahad.services.impl;

import com.ahad.dto.imports.CompanySearchDTO;
import com.ahad.dto.request.JobHistoryRequestDTO;
import com.ahad.dto.response.JobHistoryResponseDTO;
import com.ahad.dto.update.JobHistoryUpdateDTO;
import com.ahad.exceptions.ResourceNotFoundException;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.FallbackResponse;
import com.ahad.mapper.JobHistoryMapper;
import com.ahad.messages.ResponseMessage;
import com.ahad.models.JobHistory;
import com.ahad.models.UserInformation;
import com.ahad.repos.JobHistoryRepository;
import com.ahad.repos.UserInformationRepository;
import com.ahad.services.external.CompanyService;
import com.ahad.services.internal.JobHistoryService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JobHistoryServiceImpl implements JobHistoryService {

    private final JobHistoryRepository jobHistoryRepository;
    private final JobHistoryMapper jobHistoryMapper;
    private final CompanyService companyService;

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

    int retryCount = 0;

    @Override
    // @CircuitBreaker(name = "companyServiceCB", fallbackMethod =
    // "fallbackCompanyService")
    // @Retry(name = "companyServiceCB", fallbackMethod = "fallbackCompanyService")
    @RateLimiter(name = "companyServiceRateLimiter", fallbackMethod = "fallbackCompanyService")
    public JobHistoryResponseDTO getJobHistoryById(UUID id) {
        log.info("Retry count : {}", retryCount);
        retryCount++;
        JobHistory jobHistory = jobHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobHistory " +
                        ResponseMessage.ID_NOT_FOUND + id));

        // Normal call to external service
        ApiResponse<CompanySearchDTO> companyDTO = companyService.getCompanyById(jobHistory.getCompanyId());

        // ✅ Manually throw exception if null or not successful
        if (companyDTO == null || !companyDTO.isSuccess() || companyDTO.getData() == null) {
            throw new RuntimeException("Company service returned null or failure");
        }

        JobHistoryResponseDTO responseDTO = jobHistoryMapper.toDto(jobHistory);
        responseDTO.setCompanyDTO(companyDTO.getData());

        return responseDTO;
    }

    // Fallback method triggered by Circuit Breaker
    private JobHistoryResponseDTO fallbackCompanyService(UUID id, Throwable ex) {
        JobHistory jobHistory = jobHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JobHistory " +
                        ResponseMessage.ID_NOT_FOUND + id));

        JobHistoryResponseDTO responseDTO = jobHistoryMapper.toDto(jobHistory);

        // Return default Company DTO
        log.error("fallback is executed because Company service is down {}", ex.getMessage());
        responseDTO.setCompanyDTO(FallbackResponse.defaultCompanyDTO());
        responseDTO.setError("Company service is currently unavailable: " + ex.getMessage());

        return responseDTO;
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
