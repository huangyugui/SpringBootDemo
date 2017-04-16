package com.example.rabbitmq.monitor;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class JavaAPIMonitor {

    private static String queue_name = "helloword";
    
    public static void monitor1() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // amqp://userName:password@hostName:portNumber/virtualHost
        factory.setUri("amqp://admin:admin@localhost:5672/");
        Connection conn = factory.newConnection();
        // System.out.println(conn.getChannelMax());
        Channel channel = conn.createChannel();
        // System.out.println(channel.getChannelNumber());
        System.out.println(channel.messageCount(queue_name));
        System.out.println(channel.consumerCount(queue_name));
        channel.close();
        conn.close();
    }
    
    public static void monitor2() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        // amqp://userName:password@hostName:portNumber/virtualHost
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(AMQP.PROTOCOL.PORT);
        Connection conn = factory.newConnection();
         System.out.println(conn.getChannelMax());
        Channel channel = conn.createChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
         System.out.println(channel.getChannelNumber());
        System.out.println(channel.messageCount(queue_name));
        System.out.println(channel.consumerCount(queue_name));
        channel.close();
        conn.close();
    }
    
    public static void main(String[] args) throws Exception {
        monitor2();
    }
}