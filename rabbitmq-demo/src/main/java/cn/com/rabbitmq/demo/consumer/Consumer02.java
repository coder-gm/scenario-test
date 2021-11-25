package cn.com.rabbitmq.demo.consumer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/25 23:09
 */
public class Consumer02 {

    /**
     * 死信交换机名称
     */
    private static final String DEAD_EXCHANGE = "dead_exchange";


    public static void main(String[] argv) throws Exception {
        //创建信道
        Channel channel = RabbitMqUtils.getChannel();
        //创建交换机
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //创建队列
        String deadQueue = "dead-queue";
        channel.queueDeclare(deadQueue, false, false, false, null);
        //绑定交换机与队列
        channel.queueBind(deadQueue, DEAD_EXCHANGE, "lisi");


        System.out.println("等待接收死信队列消息.....");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Consumer02 接收死信队列的消息" + message);
        };
        channel.basicConsume(deadQueue, true, deliverCallback, consumerTag -> {
        });


    }


}
