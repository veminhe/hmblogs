package com.hmblogs.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Properties;

@RestController
@Slf4j
public class ConsumerController {

    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "43.138.0.199:9092";
    private static KafkaConsumer<String,String> consumer = null;

    /**
     * consumeMessage
     * @return
     */
    @GetMapping(value = "/consumeMessage")
    public void consumeKafkaMessage(){
        log.info("consumeMessage");
        Properties configs = initConfig();
        consumer = new KafkaConsumer<String, String>(configs);
        consumer.subscribe(Collections.singletonList(TOPIC));
        send();
    }

    private static Properties initConfig(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers",BROKER_LIST);
        properties.put("group.id","0");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.offset.reset", "earliest");
        return properties;
    }


    private void send() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10);
            for (ConsumerRecord<String, String> record : records) {
                log.info("Received message: key={}, value={}, partition={}, offset={}\n",
                        record.key(), record.value(), record.partition(), record.offset());
            }
        }


    }
}
