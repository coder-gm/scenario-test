package cn.com.springtxdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */
@SpringBootApplication(scanBasePackages = {
        "cn.com.springtxdemo",
        "cn.com.common.handle",
        "cn.com.common.config"
})
public class SpringTxDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringTxDemoApp.class, args);
    }

}
