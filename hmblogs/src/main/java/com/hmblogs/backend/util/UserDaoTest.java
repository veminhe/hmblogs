package com.hmblogs.backend.util;

import com.hmblogs.backend.dao.PageQuery;
import com.hmblogs.backend.dao.PageResult;
import com.hmblogs.backend.dao.MongoUserDao;
import com.hmblogs.backend.entity.MongoUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private MongoUserDao userDao;

    @Test
    public void testSaveUser() throws Exception {
        MongoUserEntity user=new MongoUserEntity();
        user.setId(4l);
        user.setUserName("aa");
        user.setPassWord("bb");
        userDao.saveUser(user);
    }

    @Test
    public void findUserByUserName(){
        MongoUserEntity user= userDao.findUserByUserName("小明");
        System.out.println("user is "+user);
    }

    @Test
    public void updateUser(){
        MongoUserEntity user=new MongoUserEntity();
        user.setId(2l);
        user.setUserName("天空");
        user.setPassWord("fffxxxx");
        userDao.updateUser(user);
    }

    @Test
    public void deleteUserById(){
        userDao.deleteUserById(2l);
    }

    @Test
    public void findUsersByCriteria(){
        MongoUserEntity userQuery = new MongoUserEntity();
        userQuery.setId(2l);
        PageQuery<MongoUserEntity> pageQuery = new PageQuery<MongoUserEntity>();
        pageQuery.setCriteria(userQuery);
        pageQuery.setPageNum(2);
        pageQuery.setPageSize(2);
        PageResult<MongoUserEntity> user= userDao.findUserByCriteria(pageQuery);
        System.out.println("user is "+user);
    }

    @Test
    public void querys(){
        userDao.querys();
    }

    @Test
    public void aggregate(){
        userDao.aggregate();
    }

    @Test
    public void upsert(){
        userDao.upsert();
    }
}


