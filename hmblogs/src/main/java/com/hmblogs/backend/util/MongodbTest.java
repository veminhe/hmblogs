package com.hmblogs.backend.util;

import com.hmblogs.backend.dao.MongoUserRepository;
import com.hmblogs.backend.entity.MongoUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

/**
 * @author ：程序员徐大大
 * @description：TODO
 * @date ：2022-04-08 20:16
 */
@SpringBootTest
public class MongodbTest {
    //注入mongoTemplate
    @Autowired
    private MongoUserRepository userRepository;

    //添加
    @Test
    public void createUser() {
        MongoUser user = new MongoUser();
        user.setAge(20);
        user.setName("张三");
        user.setEmail("3332200@qq.com");
        MongoUser user1 = userRepository.save(user);
    }

    //查询所有
    @Test
    public void findUser() {
        List<MongoUser> userList = userRepository.findAll();
        System.out.println(userList);
    }

    //id查询
    @Test
    public void getById() {
        MongoUser user = userRepository.findById("65bf3089425b826e2b08cc4b").get();
        System.out.println(user);
    }

    //条件查询
    @Test
    public void findUserList() {
        MongoUser user = new MongoUser();
        user.setName("张三");
        user.setAge(20);
        Example<MongoUser> userExample = Example.of(user); //构建条件
        List<MongoUser> userList = userRepository.findAll(userExample);
        System.out.println(userList);
    }

    //模糊查询
    @Test
    public void findUsersLikeName() {
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        MongoUser user = new MongoUser();
        user.setName("三");
        Example<MongoUser> userExample = Example.of(user, matcher);
        List<MongoUser> userList = userRepository.findAll(userExample);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findUsersPage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
//0为第一页
        Pageable pageable = PageRequest.of(0, 10, sort);
//创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        MongoUser user = new MongoUser();
        user.setName("三");
        Example<MongoUser> userExample = Example.of(user, matcher);
//创建实例
        Example<MongoUser> example = Example.of(user, matcher);
        Page<MongoUser> pages = userRepository.findAll(example, pageable);
        System.out.println(pages);
    }

    //修改
    @Test
    public void updateUser() {
        MongoUser user = userRepository.findById("65bf3089425b826e2b08cc4b").get();
        user.setName("张三_1");
        user.setAge(25);
        user.setEmail("883220990@qq.com");
        user.setCreateDate("2024-02-04 14:42:00");
        MongoUser save = userRepository.save(user);
        System.out.println(save);
    }

    //删除
    @Test
    public void delete() {
        userRepository.deleteById("65bf3089425b826e2b08cc4b");
    }

}
