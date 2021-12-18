package cn.com.springtxdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */
@MapperScan("cn.com.springtxdemo.mapper")
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
