package com.hmblogs.backend.util;

import com.hmblogs.backend.service.StockServiceImpl;
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
public class ProcedureTest {

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Test
    public void testProcedure() {
        // 请求参数
        stockServiceImpl.operateProcedure();
    }
}
