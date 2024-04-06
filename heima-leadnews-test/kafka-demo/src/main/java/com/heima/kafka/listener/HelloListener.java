package com.heima.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HelloListener {

    @KafkaListener(topics = "itcast-topic")
    public void onMessage(String message){
        if(!StringUtils.isEmpty(message)){
            System.out.println(message);
        }

    }
    @KafkaListener(topics = "user-topic")
    public void onMessage2(String message){
        if(!StringUtils.isEmpty(message)){
            System.out.println(message);
        }

    }
}