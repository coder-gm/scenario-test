package cn.com.springtxdemo.controller;


import cn.com.springtxdemo.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zhang Guang Ming
 * @since 2021-12-18
 */
@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;

    @GetMapping("/add")
    public void add() {
        departmentsService.add();
    }

}

