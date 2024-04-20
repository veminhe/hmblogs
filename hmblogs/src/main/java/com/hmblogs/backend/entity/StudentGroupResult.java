package com.hmblogs.backend.entity;

import lombok.Data;

/**
 * 通过聚合查询的结果对应的类
 */
@Data
public class StudentGroupResult {
    private String id;

    private int ageTotal;

    private String nameMax;
}
