package com.hmblogs.backend.service;

import com.alibaba.fastjson.JSON;
import com.hmblogs.backend.dao.UserMapper;
import com.hmblogs.backend.entity.User;
import com.hmblogs.backend.util.AESUtil;
import com.hmblogs.backend.util.JedisUtil;
import com.hmblogs.backend.util.PreAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    public String login(User user){
        try {
            user.setPassword(AESUtil.AESEncrypt(user.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<User> list = userMapper.findUser(user);
        if(list!=null && !list.isEmpty()) {
            Jedis jedis = JedisUtil.getJedisConn();
            String token = UUID.randomUUID().toString();
            jedis.setex(token,30*60, JSON.toJSONString(list.get(0)));
            return token;
        }
        throw new PreAuthorizeException("没有权限");
    }

    public String checkIsLoggedIn(String token){
        Jedis jedis = JedisUtil.getJedisConn();
        String str = jedis.get(token);
        if(!StringUtils.isEmpty(str)){
            return token;
        }
        return null;
    }
}
