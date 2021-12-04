package cn.com.concurrent.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */
@SpringBootApplication(scanBasePackages = {
        "cn.com.concurrent.demo",
        "cn.com.common.handle",
        "cn.com.common.config"
})
public class ConcurrentDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentDemoApp.class, args);
    }

}
