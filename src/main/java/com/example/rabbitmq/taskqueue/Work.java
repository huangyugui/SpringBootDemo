package com.example.rabbitmq.taskqueue;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Work {
    // 队列名称
    private final static String QUEUE_NAME = "workqueue-durable";

    public static void main(String[] args) throws Exception {
        // 区分不同工作进程的输出
        int hashCode = Work.class.hashCode();
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 指定用户 密码
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 指定端口
        factory.setPort(AMQP.PROTOCOL.PORT);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 设置消息持久化
        // RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        boolean durable = true; 
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        
        // 公平转发 设置最大服务转发消息数量 只有在消费者空闲的时候会发送下一条信息。
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);
        
        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, 
                    BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(hashCode + " Received Message：'" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  finally {
                    if(!"helloworld......6".equals(message)){
                        System.out.println(hashCode + " Received Done");
//                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            }
            
        };
        boolean ack = true;//打开应答机制
        channel.basicConsume(QUEUE_NAME, ack, consumer);
        

//        QueueingConsumer consumer = new QueueingConsumer(channel);
//
//        /**
//         * ack= true: Round-robin 转发 消费者被杀死，消息会丢失 ack=false:消息应答
//         * ，为了保证消息永远不会丢失，RabbitMQ支持消息应答（message acknowledgments）。
//         * 消费者发送应答给RabbitMQ，告诉它信息已经被接收和处理，然后RabbitMQ可以自由的进行信息删除。
//         * 如果消费者被杀死而没有发送应答，RabbitMQ会认为该信息没有被完全的处理，然后将会重新转发给别的消费者。
//         * 通过这种方式，你可以确认信息不会被丢失，即使消者偶尔被杀死。 消费者需要耗费特别特别长的时间是允许的。
//         * 
//         */
//
//        boolean ack = false; // 打开应答机制
//        // 指定消费队列
//        channel.basicConsume(QUEUE_NAME, ack, consumer);
//
//
//        while (true) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String message = new String(delivery.getBody());
//
//            System.out.println(hashCode + " Received Message：'" + message + "'");
//            doWork(message);
//            // 发送应答
//            if(!"helloworld......6".equals(message)){
//                System.out.println(hashCode + " Received Done");
//                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//            }
//
//        }
    }

    /**
     * 每个点耗时1s
     * 
     * @param task
     * @throws InterruptedException
     */
    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.')
                Thread.sleep(1000);
        }
    }

}