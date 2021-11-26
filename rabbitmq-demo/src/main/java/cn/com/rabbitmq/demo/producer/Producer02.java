package cn.com.rabbitmq.demo.producer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/26 23:25
 */
public class Producer02 {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        //给消息赋予一个 priority 属性,设置5 的消息的优先级
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();

        //给消息赋予一个 priority 属性,设置7 的消息的优先级
        AMQP.BasicProperties properties1 = new AMQP.BasicProperties().builder().priority(7).build();

        for (int i = 1; i < 11; i++) {
            String message = "info" + i;

            if (i == 5) {
                channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
            } else if (i == 7) {
                channel.basicPublish("", QUEUE_NAME, properties1, message.getBytes());
            } else {
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            System.out.println("发送消息完成:" + message);


        }
    }


}
