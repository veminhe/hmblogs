package com.hmblogs.backend.service;

import com.hmblogs.backend.dao.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StockServiceImpl {

    @Autowired
    private StockMapper stockMapper;

    public void operateProcedure(){
        HashMap<String,String> param = new HashMap<>();
        param.put("id1","9000");
        param.put("quantity1","20");
        param.put("id2","9900");
        param.put("quantity2","24");
        Map<String,Integer> result = stockMapper.invokeStockProdudure(param);
        log.info("result:"+result);
    }
}
