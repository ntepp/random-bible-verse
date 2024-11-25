package com.perinfinity.com.randomverse.controller;

import com.perinfinity.com.randomverse.domain.Verse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class VerseController {

    private int requestCounter = 1;
    private static final Logger log = LoggerFactory.getLogger(VerseController.class);
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String getVerse(Model model) {
        long startTime = System.currentTimeMillis(); // Track request start time

        String requestId = "req-" + requestCounter; // Unique request identifier for observability
        log.info("Processing random verse request with ID {}", requestId);

        try {
            Verse verse = restTemplate.getForObject(
                    "https://bible-api.com/?random=verse", Verse.class);
            if (verse != null) {
                model.addAttribute("reference", verse.reference());
                model.addAttribute("text", verse.text());
                model.addAttribute("translation", verse.translation_name());
                long duration = System.currentTimeMillis() - startTime;
                log.info("Request {} completed successfully. Verse reference: {}, Processing time: {}ms",
                        requestId, verse.reference(), duration);
            } else {
                log.warn("Request {} returned null verse", requestId);
            }
        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("Request {} failed after {}ms. Error: {}",
                    requestId, duration, ex.getMessage(), ex);
        }
        requestCounter++;
        return "index";
    }

}
