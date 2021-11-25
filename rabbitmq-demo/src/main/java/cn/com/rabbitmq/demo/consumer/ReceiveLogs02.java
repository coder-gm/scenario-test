package cn.com.rabbitmq.demo.consumer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 消费者
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/25 18:03
 */
public class ReceiveLogs02 {
    /**
     * 交换机名称
     */
    private static final String EXCHANGE_NAME = "LOGS_FANOUT";

    public static void main(String[] argv) throws Exception {

        //步骤1.创建信道
        Channel channel = RabbitMqUtils.getChannel();

        //步骤2：生成一个临时队列
        /**
         * 生成一个临时的队列 队列的名称是随机的
         * 当消费者断开和该队列的连接时 队列自动删除
         */
        String queueName = channel.queueDeclare().getQueue();

        //步骤3：绑定交换机与队列
        //把该临时队列绑定我们的 exchange 其中 routingkey(也称之为 binding key)为空字符串
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("消费者2 等待接收消息,把接收到的消息打印在屏幕.....");


        //步骤4：接收消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("控制台打印接收到的消息" + message);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

}
