package com.hmblogs.backend.mode.proxy;

// 实现接口的具体类
class UserServiceImpl implements UserService {
    public void addUser(String username) {
        System.out.println("添加用户：" + username);
    }
}

