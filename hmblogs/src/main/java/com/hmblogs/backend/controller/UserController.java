package com.hmblogs.backend.controller;

import com.hmblogs.backend.entity.User;
import com.hmblogs.backend.service.UserServiceImpl;
import com.hmblogs.backend.util.BlogConstant;
import com.hmblogs.backend.util.RequestAttributeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("login")
    public BlogResponse login(@RequestBody User user){
        String token = userServiceImpl.checkIsLoggedIn(RequestAttributeUtil.getCookie("token"));
        if(StringUtils.isEmpty(token)){
            token = userServiceImpl.login(user);
        }
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setCode(BlogConstant.SUCCESS_CODE);
        blogResponse.setResult(token);
        return blogResponse;
    }

    @PostMapping("checkIsLoggedIn")
    public BlogResponse checkIsLoggedIn(){
        String token = userServiceImpl.checkIsLoggedIn(RequestAttributeUtil.getCookie("token"));
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setCode(BlogConstant.SUCCESS_CODE);
        blogResponse.setResult(token);
        return blogResponse;
    }
}
