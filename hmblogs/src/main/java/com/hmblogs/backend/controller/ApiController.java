package com.hmblogs.backend.controller;

import com.hmblogs.backend.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @GetMapping("/hello")
    public String hello() {
        return restTemplateUtil.get("http://localhost:8080/hello");
    }
}

