package cn.com.rabbitmq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */
@SpringBootApplication(scanBasePackages = {
        "cn.com.rabbitmq.demo",
        "cn.com.common.handle",
        "cn.com.common.config"
})
public class RabbitmqDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApp.class, args);
    }

}
