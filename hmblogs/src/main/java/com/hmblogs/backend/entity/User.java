package com.hmblogs.backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user")
public class User {

    @TableId(value="user_id", type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String password;

    private String userRemark;
}

