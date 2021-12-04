package cn.com.java.basics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zgming
 */
@SpringBootApplication(scanBasePackages = {
        "cn.com.java.basics",
        "cn.com.common.handle",
        "cn.com.common.config"
})
public class JavaBasicsApp {

    public static void main(String[] args) {
        SpringApplication.run(JavaBasicsApp.class, args);
    }

}
