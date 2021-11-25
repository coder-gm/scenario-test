package cn.com.rabbitmq.demo.consumer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/25 19:48
 */
public class ReceiveLogsDirect01 {

    private static final String EXCHANGE_NAME = "direct_logs";


    public static void main(String[] argv) throws Exception {
        //步骤1.创建信道
        Channel channel = RabbitMqUtils.getChannel();

        //创建队列
        String queueName = "disk";
        channel.queueDeclare(queueName, false, false, false, null);

        //绑定交换机与队列之间的关系
        channel.queueBind(queueName, EXCHANGE_NAME, "error");
        System.out.println("等待接收消息.....");


        //接收消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" 接收绑定键 :"+delivery.getEnvelope().getRoutingKey()+", 消 息:"+message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }


}
