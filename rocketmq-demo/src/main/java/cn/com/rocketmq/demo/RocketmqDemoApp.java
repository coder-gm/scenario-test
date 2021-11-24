package cn.com.rocketmq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */

@SpringBootApplication(scanBasePackages = {
        "cn.com.common.handle",
        "cn.com.common.config"
})
public class RocketmqDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqDemoApp.class, args);
    }

}
