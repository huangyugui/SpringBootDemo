package com.example.rabbitmq.delayQueue2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    private static String queue_name = "test.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(queue_name, true, false, false, null);
        String message = "hello world!" + System.currentTimeMillis();
        channel.basicPublish("delaysync.exchange", "deal.message", null, message.getBytes());
        System.out.println("sent message: " + message + ",date:" + System.currentTimeMillis());
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}







