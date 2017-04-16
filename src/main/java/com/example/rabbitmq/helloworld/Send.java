package com.example.rabbitmq.helloworld;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    // 消息队列名称
    private final static String QUEUE_NAME = "helloword";

    public static void main(String[] args) throws Exception {
        /**
         * 创建连接连接到MabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();
        // 设置MabbitMQ所在主机ip或者主机名
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
        // 指定一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //如果排他性设置为true，那么同一个Connection中其他channel可以访问这个queue，
        //如果有Connection声明了一个排他Queue，其他Connection是不允许使用该Queue
        //即使是持久化的，一旦连接关闭或者客户端退出，该排他Queue会被自动删除，只限于一个客户端发送读取消息的应用场景
//        channel.queueDeclare(QUEUE_NAME, false, true, false, null);
        // 发送的消息
        String message = "hello world!";
        // 往队列中发出一条消息
        for(int i = 0; i < 5; i++){
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent Message：'" + message + "'");
        }
        TimeUnit.SECONDS.sleep(10);
        // 关闭频道和连接
        channel.close();
        connection.close();
    }

}