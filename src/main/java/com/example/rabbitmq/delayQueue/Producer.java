package com.example.rabbitmq.delayQueue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

    private static String queue_name = "delay.1s.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 指定端口
        factory.setPort(AMQP.PROTOCOL.PORT);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // 声明队列
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("x-message-ttl", 3000);
        params.put("x-max-length", 5);
        params.put("x-dead-letter-exchange", "delay.exchange1");
        channel.queueDeclare(queue_name, true, false, false, params);
//        channel.queueDeclare(queue_name, true, false, false, null);
        
        for(int i = 0; i < 10; i++){
            String message = "hello world!" + "=====>>>>>" + i;
            channel.basicPublish("", queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("sent message: " + message + "=====>>>>>" + i);
        }
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}







