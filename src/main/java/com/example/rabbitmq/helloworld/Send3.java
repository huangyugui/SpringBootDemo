package com.example.rabbitmq.helloworld;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class Send3 {
    // 消息队列名称
    private final static String QUEUE_NAME = "helloword";

    public static void main(String[] args) throws Exception {
        
        CachingConnectionFactory  cf = new CachingConnectionFactory("localhost");
        cf.setUsername("admin");
        cf.setPassword("admin");
        RabbitTemplate rt = new RabbitTemplate(cf);
        rt.send("", "helloworld", new Message("hello".getBytes(), null));
        
    }

}