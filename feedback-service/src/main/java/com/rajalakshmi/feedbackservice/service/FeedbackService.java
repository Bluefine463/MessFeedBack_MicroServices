package com.rajalakshmi.feedbackservice.service;

import com.rajalakshmi.feedbackservice.dto.FeedbackRequest;
import com.rajalakshmi.feedbackservice.model.Feedback;
import com.rajalakshmi.feedbackservice.repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;
    private final BlobStorageService blob;

    public FeedbackService(FeedbackRepository repository, BlobStorageService blob) {
        this.repository = repository;
        this.blob = blob;
    }

    public Feedback saveFeedback(FeedbackRequest req, MultipartFile file) throws Exception {
        Feedback f = new Feedback();
        f.setStudentId(req.getStudentId());
        f.setMessId(req.getMessId());
        f.setRating(req.getRating());
        f.setComments(req.getComments());
        if (file != null && !file.isEmpty()) {
            String url = blob.uploadFile(file);
            f.setImageUrl(url);
        } else if (req.getImageUrl() != null) {
            f.setImageUrl(req.getImageUrl());
        }
        return repository.save(f);
    }

    public List<Feedback> getByDate(String messId, LocalDate date) {
        OffsetDateTime start = date.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = date.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);
        if (messId != null) return repository.findByMessIdAndCreatedAtBetween(messId, start, end);
        return repository.findByCreatedAtBetween(start, end);
    }
}

