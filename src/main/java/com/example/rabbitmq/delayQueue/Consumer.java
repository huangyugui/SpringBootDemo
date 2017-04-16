package com.example.rabbitmq.delayQueue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer {

    private static String queue_name = "delay.1s.queue";

    public static void main(String[] args) throws Exception {
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

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, 
                    BasicProperties properties, byte[] body) throws IOException {
                long currentTimeMillis = System.currentTimeMillis();
                try {
                    String message = new String(body, "UTF-8");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("received message:" + message + ",date:" + currentTimeMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(currentTimeMillis + " Received Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
            
        };
        boolean ack = false;//打开应答机制
        channel.basicConsume(queue_name, ack, consumer);
        
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        // 指定消费队列
//        channel.basicConsume(queue_name, true, consumer);
//        while (true) {
//            // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String message = new String(delivery.getBody());
//            TimeUnit.SECONDS.sleep(5);
//            System.out.println("received message:" + message + ",date:" + System.currentTimeMillis());
//        }
    }

}
