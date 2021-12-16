package cn.com.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/15 22:49
 */
@RestController
@RequestMapping("/docker")
public class DockerDemoController {

    @GetMapping("/demo")
    public String dockerDemo() {
        return "Idea 整合 Docker 功能Java测试 ！！";
    }

    @GetMapping("/demo1")
    public String dockerDemo1() {
        return "Idea 整合 Docker 功能Java测试1 ！！";
    }


}
