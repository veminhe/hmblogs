package com.hmblogs.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Properties;

@RestController
@Slf4j
public class ProducerController {

    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "43.138.0.199:9092";
    private static KafkaProducer<String,String> producer = null;

    /**
     * sendMessage
     * @return
     */
    @GetMapping(value = "/sendMessage")
    public void redisTestLock(){
        log.info("sendMessage");
        Properties configs = initConfig();
        producer = new KafkaProducer<String, String>(configs);
        send();
    }

    /*
    初始化配置
     */
    private Properties initConfig(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return properties;
    }

    private void send() {
        //消息实体
        ProducerRecord<String , String> record = null;
        for (int i = 0; i < 3; i++) {
            record = new ProducerRecord<String, String>(TOPIC, "abcde："+(int)(10*(Math.random())));
            //发送消息
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e){
                        log.error("send error:" + e.getMessage());
                    }else {
                        log.info(String.format("offset:%s,partition:%s",recordMetadata.offset(),recordMetadata.partition()));
                    }
                }
            });
        }
        producer.close();
    }
}
