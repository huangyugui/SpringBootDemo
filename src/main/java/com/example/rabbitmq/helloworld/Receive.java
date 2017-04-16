package com.example.rabbitmq.helloworld;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receive {

    // 消息队列名称
    private final static String QUEUE_NAME = "helloword";

    public static void main(String[] args) throws Exception {
        // 打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 指定用户 密码
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 指定端口
        factory.setPort(AMQP.PROTOCOL.PORT);
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个频道
        Channel channel = connection.createChannel();
        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, 
                    BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received Message：'" + message + "'");
            }
            
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

//        // 创建队列消费者
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        // 指定消费队列
//        channel.basicConsume(QUEUE_NAME, true, consumer);
//        while (true) {
//            // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String message = new String(delivery.getBody());
//            System.out.println("Received Message：'" + message + "'");
//        }
    }

}