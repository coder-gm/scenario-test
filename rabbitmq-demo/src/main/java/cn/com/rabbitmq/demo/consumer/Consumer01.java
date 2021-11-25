package cn.com.rabbitmq.demo.consumer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/25 23:08
 */
public class Consumer01 {

    /**
     * 普通交换机名称
     */
    private static final String NORMAL_EXCHANGE = "normal_exchange";

    /**
     * 死信交换机名称
     */
    private static final String DEAD_EXCHANGE = "dead_exchange";


    public static void main(String[] argv) throws Exception {
        //创建信道
        Channel channel = RabbitMqUtils.getChannel();

        ////////////////////////////////////////////死信//////////////////////////////////////////

        //声明普通交换机 类型为 direct
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        //声明死信队列
        String deadQueue = "dead-queue";
        channel.queueDeclare(deadQueue, false, false, false, null);

        //死信队列绑定死信交换机与 routingkey
        channel.queueBind(deadQueue, DEAD_EXCHANGE, "lisi");


        ////////////////////////////////////////////普通//////////////////////////////////////////

        //声明死信交换机 类型为 direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

        //这个params的作用是如果普通队列没有正常消费，就把当前的死信队列信息传递给死信队列中，让死信队列去消费
        //正常队列绑定死信队列信息
        Map<String, Object> params = new HashMap<>(2);
        //设置过期时间 10s=10000ms,这个过期时间也可以在生产方发送过来（实际上生产方发送的比较多）
        //params.put("x-message-ttl", 100000);
        //正常队列设置死信交换机 参数 key 是固定值
        params.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //正常队列设置死信 routing-key 参数 key 是固定值
        params.put("x-dead-letter-routing-key", "lisi");
        //设置正常队列的长度限制，普通队列长度只能是6 ，如果长度超过6 就走死信队列
        //params.put("x-max-length", 6);

        String normalQueue = "normal-queue";
        channel.queueDeclare(normalQueue, false, false, false, params);

        //普通队列绑定死信交换机与 routingkey
        channel.queueBind(normalQueue, NORMAL_EXCHANGE, "zhangsan");


        ////////////////////////////////////////////等待接收消息//////////////////////////////////////////

        System.out.println("等待接收消息.....");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            if (message.equals("info5")) {
                System.out.println("Consumer01 接收到消息" + message + "并拒绝签收该消息");
                //requeue 设置为 false 代表拒绝重新入队 该队列如果配置了死信交换机将发送到死信队列中
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("Consumer01 接收到消息" + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(normalQueue, false, deliverCallback, consumerTag -> {
        });


    }


}
