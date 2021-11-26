package cn.com.rabbitmq.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 报警消费者
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/26 21:58
 */
@Component
@Slf4j
public class WarningConsumer {


    @RabbitListener(queues = "warning.queue")
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("报警发现不可路由消息：{}", msg);
    }


}