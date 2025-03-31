package com.example.expenseTracker.controller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/")
public class WebController {

    @GetMapping
    public String getHtmlPage() throws IOException {
        Resource resource = new ClassPathResource("static/index.html");
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }
}
