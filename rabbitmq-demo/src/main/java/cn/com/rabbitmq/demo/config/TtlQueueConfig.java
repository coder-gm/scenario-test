package cn.com.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 有过期时间的队列
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/11/26 8:59
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机名称
    public static final String X_EXCHANGE = "X";
    //普通队列的名称
    public static final String QUEUE_A = "QA";
    ////普通队列的名称
    //public static final String QUEUE_B = "QB";

    //死信交换机名称
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    //死信队列的名称
    public static final String DEAD_LETTER_QUEUE = "QD";


    ///////////////////////////////////////////声明交换机//////////////////////////////////////////////

    /**
     * 创建一个直接交换机 xExchange
     *
     * @return
     */
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 创建一个直接交换机 yExchange
     *
     * @return
     */
    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    ///////////////////////////////////////////声明队列//////////////////////////////////////////////

    /**
     * 声明队列 QA ttl 为 10s 并绑定到对应的死信交换机
     *
     * @return
     */
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "YD");
        //声明队列的 TTL
        //args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }


    ///**
    // * 声明队列 QB ttl 为 40s 并绑定到对应的死信交换机
    // *
    // * @return
    // */
    //@Bean("queueB")
    //public Queue queueB() {
    //    Map<String, Object> args = new HashMap<>(3);
    //    //声明当前队列绑定的死信交换机
    //    args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
    //    //声明当前队列的死信路由 key
    //    args.put("x-dead-letter-routing-key", "YD");
    //    //声明队列的 TTL
    //    args.put("x-message-ttl", 40000);
    //    return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    //}


    /**
     * 声明死信队列 QD
     *
     * @return
     */
    @Bean("queueD")
    public Queue queueD() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    ///////////////////////////////////////////队列与交换机绑定//////////////////////////////////////////////

    /**
     * 声明队列 QA 绑定 xExchange(普通) 交换机
     *
     * @param queueA
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueaBindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    //
    ///**
    // * 声明队列 QB 绑定 X 交换机
    // *
    // * @param queue1B
    // * @param xExchange
    // * @return
    // */
    //@Bean
    //public Binding queuebBindingX(@Qualifier("queueB") Queue queue1B,
    //                              @Qualifier("xExchange") DirectExchange xExchange) {
    //    return BindingBuilder.bind(queue1B).to(xExchange).with("XB");
    //}


    /**
     * 声明死信队列 QD 绑定关系
     *
     * @param queueD
     * @param yExchange
     * @return
     */
    @Bean
    public Binding deadLetterBindingQAD(@Qualifier("queueD") Queue queueD,
                                        @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }


}