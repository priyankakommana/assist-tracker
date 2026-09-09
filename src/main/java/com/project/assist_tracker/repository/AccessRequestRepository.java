package com.project.assist_tracker.repository;

import com.project.assist_tracker.model.AccessRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {
}