package com.ahad.repos;

import com.ahad.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AchievementRepository extends JpaRepository<Achievement, UUID> {
    List<Achievement> findByUserInformationId(UUID userInformationId);
}
