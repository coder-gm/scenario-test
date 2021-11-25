package cn.com.rabbitmq.demo.producer;

import cn.com.rabbitmq.demo.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * EmitLog 发送消息给两个消费者接收
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/25 17:51
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "LOGS_FANOUT";

    public static void main(String[] argv) throws Exception {

        //步骤1.创建信道
        Channel channel = RabbitMqUtils.getChannel();

        //步骤2.创建交换机
        /**
         * 声明一个 exchange
         * 1.exchange 的名称
         * 2.exchange 的类型
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");


        //步骤3.发送消息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入信息");
        while (sc.hasNext()) {
            String message = sc.nextLine();
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息" + message);
        }
    }


}
