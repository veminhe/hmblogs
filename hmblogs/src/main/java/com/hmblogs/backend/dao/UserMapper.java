package com.hmblogs.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmblogs.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> findUser(User user);
    
}

