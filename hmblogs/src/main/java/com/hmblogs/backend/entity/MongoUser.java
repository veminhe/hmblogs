package com.hmblogs.backend.entity;

import lombok.Data;

/**
 * @author ：heming
 * @description：TODO
 * @date ：2024-02-04 14:26
 */
@Data
//@Document("User")
public class MongoUser {
    //@Id
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String createDate;

}
