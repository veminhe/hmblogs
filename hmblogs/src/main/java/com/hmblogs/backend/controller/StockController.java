package com.hmblogs.backend.controller;

import com.hmblogs.backend.dao.StockMapper;
import com.hmblogs.backend.entity.Stock;
import com.hmblogs.backend.util.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class StockController {

    @Autowired
    private StockMapper stockMapper;


    @PreAuthorize(hasPermi = "system:stock:list")
    /**
     * findAllStocks
     * @return
     */
    @GetMapping(value = "/findAllStocks")
    public List<Stock> findAllStocks(){
        return stockMapper.findAll();
    }

    private static final Integer K = 1024;
    /**
     * 死循环，验证JVM调优
     * @return
     */
    @GetMapping(value = "/deadLoop")
    public void deadLoop(){
        int size = K * K * 8;
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            log.info("deadLoop"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new byte[size]);
        }

    }

    /**
     * redis test
     * @return
     */
    @GetMapping(value = "/reduceStock")
    public void redisTestLock(){
        log.info("reduceStock");
        int id = 1111;
        String key = "reduceStock"+id;
        String time = new Date().getTime()+"";
        Jedis jedisCom = new Jedis("localhost",6379);
        jedisCom.auth("heming");
        if (jedisCom.setnx(key, time)==1) {
            log.info("{}生成锁...", time);
            try{
                Stock stock = new Stock();
                stock.setId(id);
                Stock stockDo = stockMapper.findById(stock);
                Integer quantity = stockDo.getQuantity();
                if(quantity!=null && quantity>0){
                    stockMapper.updateStockById(stock);
                    log.info("update success.");
                }else{
                    log.info("no update.");
                }
            } finally {
                log.info("{}释放锁...", time);
                //释放锁
                jedisCom.del(key, time);
            }
        }
    }
}
