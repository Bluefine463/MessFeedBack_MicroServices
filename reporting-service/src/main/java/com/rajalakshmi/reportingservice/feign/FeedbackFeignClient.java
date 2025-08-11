package com.rajalakshmi.reportingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient(name = "feedback-service", url = "${FEEDBACK_SERVICE_URL:http://feedback-service.azurewebsites.net}")
public interface FeedbackFeignClient {
    @GetMapping("/api/feedback")
    List<Map<String,Object>> getFeedbacks(@RequestParam(value="messId", required=false) String messId,
                                          @RequestParam(value="date", required=false) String date);
}
