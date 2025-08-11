package com.rajalakshmi.feedbackservice.controller;

import com.rajalakshmi.feedbackservice.dto.FeedbackRequest;
import com.rajalakshmi.feedbackservice.model.Feedback;
import com.rajalakshmi.feedbackservice.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) { this.service = service; }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Feedback> submit(@RequestPart("payload") @Valid FeedbackRequest payload,
                                           @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        Feedback saved = service.saveFeedback(payload, file);
        return ResponseEntity.ok(saved);
    }

    @PostMapping(path="/json", consumes = "application/json")
    public ResponseEntity<Feedback> submitJson(@RequestBody @Valid FeedbackRequest payload) throws Exception {
        Feedback saved = service.saveFeedback(payload, null);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> get(@RequestParam(value = "messId", required = false) String messId,
                                              @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) date = LocalDate.now();
        List<Feedback> list = service.getByDate(messId, date);
        return ResponseEntity.ok(list);
    }
}
