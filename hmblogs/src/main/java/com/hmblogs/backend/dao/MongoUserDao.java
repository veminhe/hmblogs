package com.hmblogs.backend.dao;

import com.hmblogs.backend.entity.MongoUserEntity;

public interface MongoUserDao {
    public void saveUser(MongoUserEntity user);

    public MongoUserEntity findUserByUserName(String userName);

    public void updateUser(MongoUserEntity user);

    public void deleteUserById(Long id);

    public PageResult<MongoUserEntity> findUserByCriteria(PageQuery<MongoUserEntity> pageQuery);

    public void querys();

    public void aggregate();

    public void upsert();
}
