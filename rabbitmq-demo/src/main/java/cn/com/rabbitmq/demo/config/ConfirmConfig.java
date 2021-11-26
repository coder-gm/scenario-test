package cn.com.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/26 16:58
 */
@Configuration
public class ConfirmConfig {

    //确认交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";

    //确认队列
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";

    //备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";

    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";

    //警告队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";



    ///////////////////////////////////确认交换机和确认队列//////////////////////////////////////////

    /**
     * 声明确认 Exchange 交换机的备份交换机
     *
     * @return
     */
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true)
                //设置该交换机的备份交换机
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME);
        return (DirectExchange) exchangeBuilder.build();
    }


    /**
     * 声明确认队列
     *
     * @return
     */
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    /**
     * 声明确认队列绑定关系
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding queueBinding(@Qualifier("confirmQueue") Queue queue,
                                @Qualifier("confirmExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("key1");
    }




    ///////////////////////////////////备份交换机和备份队列//////////////////////////////////////////

    /**
     * 声明备份 Exchange
     *
     * @return
     */
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 声明备份队列
     *
     * @return
     */
    @Bean("backQueue")
    public Queue backQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }


    /**
     * 声明警告队列
     *
     * @return
     */
    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    /**
     * 声明报警队列绑定关系
     *
     * @param queue
     * @param backupExchange
     * @return
     */
    @Bean
    public Binding warningBinding(@Qualifier("warningQueue") Queue queue,
                                  @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(queue).to(backupExchange);
    }


    /**
     * 声明备份队列绑定关系
     *
     * @param queue
     * @param backupExchange
     * @return
     */
    @Bean
    public Binding backupBinding(@Qualifier("backQueue") Queue queue,
                                 @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(queue).to(backupExchange);
    }


}
