package cn.com.rocketmq.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */
@MapperScan(basePackages = {
})
@SpringBootApplication(scanBasePackages = {
        "cn.com.common.handle",
        "cn.com.common.config",
        "cn.com.common.service",
})
public class RocketmqDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqDemoApp.class, args);
    }

}
