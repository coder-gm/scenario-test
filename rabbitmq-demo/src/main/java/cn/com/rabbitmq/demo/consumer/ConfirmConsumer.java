package cn.com.rabbitmq.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/26 17:13
 */
@Component
@Slf4j
public class ConfirmConsumer {


    @RabbitListener(queues ="confirm.queue")
    public void receiveMsg(Message message){
        String msg=new String(message.getBody());
        log.info("接受到队列 confirm.queue 消息:{}",msg);
    }


}