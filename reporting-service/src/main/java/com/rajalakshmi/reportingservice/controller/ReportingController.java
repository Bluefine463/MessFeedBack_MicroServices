package com.rajalakshmi.reportingservice.controller;


import com.rajalakshmi.reportingservice.feign.FeedbackFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportingController {


    private final FeedbackFeignClient client;

    public ReportingController(FeedbackFeignClient client) { this.client = client; }

    @GetMapping("/daily")
    public ResponseEntity<Map<String,Object>> daily(@RequestParam(value="date", required=false) String date,
                                                    @RequestParam(value="messId", required=false) String messId) {
        List<Map<String,Object>> list = client.getFeedbacks(messId, date);
        int total = list.size();
        double avg = list.stream().mapToInt(m -> ((Number)m.getOrDefault("rating",0)).intValue()).average().orElse(0.0);
        long low = list.stream().filter(m -> ((Number)m.getOrDefault("rating",0)).intValue() <= 2).count();
        Map<String,Object> resp = Map.of("date", date == null ? java.time.LocalDate.now().toString() : date,"total", total, "avg", avg, "low", low);
        return ResponseEntity.ok(resp);
    }
}
