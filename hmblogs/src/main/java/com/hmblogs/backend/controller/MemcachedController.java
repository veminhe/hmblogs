package com.hmblogs.backend.controller;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;


@RestController
@RequestMapping("/memcached")
@Slf4j
public class MemcachedController {

    @GetMapping("/test")
    public void hello() {
        try{
            // 连接本地的 Memcached 服务
            MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            log.info("Connection to server successful.");

            // 存储数据
            Future fo = mcc.set("runoob", 900, "Free Education");

            // 查看存储状态
            log.info("set status:" + fo.get());

            // 输出值
            log.info("runoob value in cache - " + mcc.get("runoob"));

            // 关闭连接
            mcc.shutdown();

        }catch(Exception ex){
            log.info( "cuowule:"+ex.getMessage() );
        }
    }
}


