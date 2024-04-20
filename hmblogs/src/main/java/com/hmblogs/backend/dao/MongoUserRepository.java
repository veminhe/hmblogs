package com.hmblogs.backend.dao;

import com.hmblogs.backend.entity.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

//实现Mongodb的增删改查方法
public interface MongoUserRepository extends MongoRepository<MongoUser,String> {
}



