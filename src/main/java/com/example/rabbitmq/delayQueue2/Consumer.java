package com.example.rabbitmq.delayQueue2;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;  
import com.rabbitmq.client.Connection;  
import com.rabbitmq.client.ConnectionFactory;  
import com.rabbitmq.client.QueueingConsumer;  
  
public class Consumer {  
  
    private static String queue_name = "test.queue";  
  
    public static void main(String[] args) throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        factory.setUsername("admin");  
        factory.setPassword("admin");  
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        // 声明队列  
        channel.queueDeclare(queue_name, true, false, false, null);  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        // 指定消费队列  
        channel.basicConsume(queue_name, true, consumer);  
        while (true) {  
            // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            TimeUnit.SECONDS.sleep(10000);
            System.out.println("received message:" + message + ",date:" + System.currentTimeMillis());  
        }  
    }  
  
}  








