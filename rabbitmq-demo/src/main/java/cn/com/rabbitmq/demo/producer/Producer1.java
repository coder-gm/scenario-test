package cn.com.rabbitmq.demo.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者 (发送MQ消息)
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/24 21:50
 */
public class Producer1 {

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

        //步骤7：生成一个队列
        /**
         * 参数1.队列名称
         * 参数2.队列里面的消息是否持久化 默认消息存储在内存中
         * 参数3.该队列是否只供一个消费者进行消费 是否进行共享 true 可以多个消费者消费
         * 参数4.是否自动删除 最后一个消费者端开连接以后 该队列是否自动删除 true 自动删除
         * 参数5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //步骤8：发送一个消息
        String message = "hello world";
        /**
         * 参数1.发送到那个交换机
         * 参数2.路由的 key 是哪个
         * 参数3.其他的参数信息
         * 参数4.发送消息的消息体
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送完毕");

    }


}
