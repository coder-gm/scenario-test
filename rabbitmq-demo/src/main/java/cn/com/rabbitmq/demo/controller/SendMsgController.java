package cn.com.rabbitmq.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/26 9:27
 */
@Slf4j
@RequestMapping("/ttl")
@RestController
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 " + ttlTime + "ms 的队列: " + message, msg -> {
            //发送消息的时候，设置消息过期时间
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });

    }


    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    @GetMapping("sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {

        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    //发送消息的时候 延迟时间， 单位：ms
                    correlationData.getMessageProperties().setDelay(delayTime);
                    return correlationData;

                });
        log.info(" 当前时间：{},发送一条延迟:{} 毫秒的信息给队列 delayed.queue:{}", new Date(), delayTime, message);
    }


}