package cn.com.rabbitmq.demo.producer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * 生产者
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/24 23:26
 */
public class WorkerProducer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel();) {

            //生成一个队列
            /**
             * 参数1.队列名称
             * 参数2.队列里面的消息是否持久化 默认消息存储在内存中
             * 参数3.该队列是否只供一个消费者进行消费 是否进行共享 true 可以多个消费者消费
             * 参数4.是否自动删除 最后一个消费者端开连接以后 该队列是否自动删除 true 自动删除
             * 参数5.其他参数
             */
            //让消息队列持久化
            boolean durable=true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            //从控制台当中接受信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {

                String message = scanner.next();
                /**
                 * 参数1.发送到那个交换机
                 * 参数2.路由的 key 是哪个
                 * 参数3.其他的参数信息
                 * 参数4.发送消息的消息体
                 */
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("发送消息完成:" + message);

            }
        }
    }


}
