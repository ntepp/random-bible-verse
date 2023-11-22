package com.perinfinity.com.randomverse.controller;

import com.perinfinity.com.randomverse.RandomVersesApplication;
import com.perinfinity.com.randomverse.domain.Verse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class VerseController {

    private int request = 1;
    private static final Logger log = LoggerFactory.getLogger(VerseController.class);
    @Autowired RestTemplate restTemplate;
    @GetMapping("/")
    public String getVerse(Model model) {
        log.info("{} request to Bible verse ", request++);
        Verse verse = restTemplate.getForObject(
                "https://bible-api.com/?random=verse", Verse.class);
        model.addAttribute("reference", verse.reference());
        model.addAttribute("text", verse.text());
        model.addAttribute("translation", verse.translation_name());
        return "index";
    }

}
