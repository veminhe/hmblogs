package com.hmblogs.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmblogs.backend.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    List<Stock> findAll();

    Stock findById(Stock stock);

    Integer updateStockById(Stock stock);

    Map<String,Integer> invokeStockProdudure(Map<String,String> param);
}

