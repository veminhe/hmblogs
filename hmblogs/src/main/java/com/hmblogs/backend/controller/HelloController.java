package com.hmblogs.backend.controller;

import com.hmblogs.backend.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    TestUserService userService;

    @GetMapping("/test")
    public String test(Integer id) {
        return userService.getUserById(id);
    }
}
