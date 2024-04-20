package com.hmblogs.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchClientSqlDslTest {

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Test
    public void complexQueryEsData() {
        // 请求参数
        Map map = new HashMap<>();
        map.put("query", "SELECT id,name,age FROM users order by name desc limit 6");
        String url = "http://43.138.0.199:9200/_sql?format=json";
        String studentData = restTemplateUtil.post(url, map);
        log.info("studentData:"+studentData);
    }
}
