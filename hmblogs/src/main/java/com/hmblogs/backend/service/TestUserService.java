package com.hmblogs.backend.service;

import org.springframework.stereotype.Service;

@Service
public class TestUserService {
    public String getUserById(Integer id) {
        System.out.println("getUserById(" + id + ")...");
        // 等待2秒
        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        return "hangge";
    }
}
