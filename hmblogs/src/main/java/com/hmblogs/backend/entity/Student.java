package com.hmblogs.backend.entity;

import lombok.Data;

/**
 * mongo集合对应的类
 */
@Data
public class Student {
    private Long id;
    private String name;
    private int age;
    private String sex;

    //getter、setter省略
}
