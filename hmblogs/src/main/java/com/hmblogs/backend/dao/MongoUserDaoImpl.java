package com.hmblogs.backend.dao;

import com.hmblogs.backend.entity.StudentGroupResult;
import com.hmblogs.backend.entity.MongoUserEntity;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregationOptions;

@Component
public class MongoUserDaoImpl implements MongoUserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param user
     */
    @Override
    public void saveUser(MongoUserEntity user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    @Override
    public MongoUserEntity findUserByUserName(String userName) {
        Query query=new Query(Criteria.where("userName").is(userName));
        MongoUserEntity user =  mongoTemplate.findOne(query , MongoUserEntity.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    @Override
    public void updateUser(MongoUserEntity user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update, MongoUserEntity.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, MongoUserEntity.class);
    }

    @Override
    public PageResult<MongoUserEntity> findUserByCriteria(PageQuery<MongoUserEntity> pageQuery) {
        int pageNum = pageQuery.getPageNum();
        int pageSize = pageQuery.getPageSize();
        MongoUserEntity userEntityQuery = pageQuery.getCriteria();
        Query query = new Query(Criteria.where("id")
                .gt(userEntityQuery.getId()));
        long count = mongoTemplate.count(query, MongoUserEntity.class);
        // 10. 分页
        // 11. 排序
        query.with(PageRequest.of(pageNum-1, pageSize,
                    Sort.by(Sort.Order.desc("id"))));
        List<MongoUserEntity> users =  mongoTemplate.find(query , MongoUserEntity.class);
        PageResult<MongoUserEntity> pageResult = new PageResult<MongoUserEntity>();
        pageResult.setList(users);
        pageResult.setTotalRecord(count);
        return pageResult;
    }

    @Override
    public void querys() {
        String name = "a";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");//采用正则表达式进行匹配
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Criteria criteria = Criteria.where("userName")
                .regex(pattern);
        Query query = new Query(); //条件构建部分
        query.addCriteria(criteria);
        query.addCriteria(Criteria.where("passWord").is("bb"));
        List<MongoUserEntity> users =  mongoTemplate.find(query , MongoUserEntity.class);
        System.out.println("user is "+users);
    }

    @Override
    public void aggregate() {
        List<AggregationOperation> operations = Lists.newArrayList();
        Criteria criteria = new Criteria();
        // todo 增加过滤条件
        operations.add(Aggregation.match(criteria));
        GroupOperation groupOperation = new GroupOperation(Fields.fields("sex")).
                sum("age").as("ageTotal").
                max("name").as("nameMax");

        operations.add(groupOperation);
        Aggregation aggregation = Aggregation.newAggregation(operations)
                .withOptions(newAggregationOptions().allowDiskUse(true).build());
        AggregationResults<StudentGroupResult> aggregationResults =
                mongoTemplate.aggregate(aggregation, "Student", StudentGroupResult.class);
        System.out.println(aggregationResults.getMappedResults());
    }

    @Override
    public void upsert() {
        Query query=new Query(Criteria.where("id").is(6));
        Update update= new Update().set("userName", "wangwu66").set("passWord", "pp66");
        //更新查询返回结果集的第一条
        mongoTemplate.upsert(query,update,"Student");
    }
}


