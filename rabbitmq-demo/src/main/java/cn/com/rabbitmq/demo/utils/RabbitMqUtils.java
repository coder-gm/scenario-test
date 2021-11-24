package cn.com.rabbitmq.demo.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/24 23:18
 */
public class RabbitMqUtils {

    /**
     * 得到一个连接的 channel (信道)
     *
     * @return
     * @throws Exception
     */
    public static Channel getChannel() throws Exception {
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

        return channel;
    }


}