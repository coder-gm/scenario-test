package cn.com.rabbitmq.demo.consumer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 工作线程2（消费者2）
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/24 23:25
 */
public class WorkerConsumer02 {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        //接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("C2消费者，接收消息中.....");

            try {
                //沉睡时间 5 秒
                Thread.sleep(18000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String receivedMessage = new String(message.getBody());
            System.out.println("接收到消息:" + receivedMessage);

            System.out.println("消息的标记tag:" + message.getEnvelope().getDeliveryTag());
            //手动应答确认是否消费
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        //取消消费的一个回调接口 如在消费的时候队列被删除掉了
        //取消消息的回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
        };

        System.out.println("C2 消费者启动等待消费......");

        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
         * 3.消费者未成功消费的回调
         * 4.取消消息的回调
         */
        //采用手动应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }


}
