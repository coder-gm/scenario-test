package cn.com.rabbitmq.demo.consumer;

import com.rabbitmq.client.*;

/**
 * 消费者 （消费消息）
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/24 22:18
 */
public class Consumer1 {

    /**
     * 路由名称
     */
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //步骤1：创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //步骤2：工厂IP 连接 RabbitMQ的队列
        factory.setHost("106.15.187.228");
        //步骤3：用户名
        factory.setUsername("zgming");
        //步骤4：密码
        factory.setPassword("Gm859230");
        //步骤5：创建连接
        Connection connection = factory.newConnection();
        //步骤6：创建信道
        Channel channel = connection.createChannel();
        System.out.println("等待接收消息....");

        //接收消息
        DeliverCallback deliverCallback = (consumerTag, msg) -> {
            String message = new String(msg.getBody());
            System.out.println(message);
        };


        //取消消费的一个回调接口 如在消费的时候队列被删除掉了
        //取消消息的回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断");
        };

        //步骤7：消费者消费消息

        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
         * 3.消费者未成功消费的回调
         * 4.取消消息的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }


}
