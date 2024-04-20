package com.hmblogs.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import static junit.framework.Assert.*;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;


@RestController
@RequestMapping("/memcached2")
@Slf4j
public class Memcached2Controller {

    @GetMapping("/test")
    public void hello() {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses("43.138.0.199:11211"),
                new int[]{1, 1, 1, 1});
        log.info("aaaa");
        // 设置连接池大小，即客户端个数
        builder.setConnectionPoolSize(50);

        // 宕机报警
        builder.setFailureMode(true);

        // 使用二进制文件
        builder.setCommandFactory(new BinaryCommandFactory());
        log.info("bbbb");
        MemcachedClient memcachedClient = null;
        try {
            memcachedClient = builder.build();
            log.info("cccc");
            try {
                // 设置/获取
                memcachedClient.set("zlex", 36000, "set/get");
                log.info("zlex:" + memcachedClient.get("zlex"));
                assertEquals("set/get", memcachedClient.get("zlex"));

                // 替换
                memcachedClient.replace("zlex", 36000, "replace");
                assertEquals("replace", memcachedClient.get("zlex"));

                // 移除
                memcachedClient.delete("zlex");
                assertNull(memcachedClient.get("zlex"));
                log.info("dddd");
            } catch (TimeoutException e) {
                // TODO Auto-generated catch block
                log.error("TimeoutException:{}", e);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                log.error("InterruptedException:{}", e);
            } catch (MemcachedException e) {
                // TODO Auto-generated catch block
                log.error("MemcachedException:{}", e);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("error:{}", e);
        } finally {
            log.info("eeee");
            if (memcachedClient != null) {
                try {
                    memcachedClient.shutdown();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("shutdown error:{}", e);
                }
            }
        }
    }
}


