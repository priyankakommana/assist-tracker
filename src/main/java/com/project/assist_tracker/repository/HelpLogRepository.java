package com.project.assist_tracker.repository;

import com.project.assist_tracker.model.HelpLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpLogRepository extends JpaRepository<HelpLog, Long> {
}