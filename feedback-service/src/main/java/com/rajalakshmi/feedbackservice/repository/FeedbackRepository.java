package com.rajalakshmi.feedbackservice.repository;

import com.rajalakshmi.feedbackservice.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByMessIdAndCreatedAtBetween(String messId, OffsetDateTime start, OffsetDateTime end);
    List<Feedback> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
}
