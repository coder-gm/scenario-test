package cn.com.springtxdemo.service.impl;

import cn.com.springtxdemo.mapper.DepartmentsMapper;
import cn.com.springtxdemo.model.po.Departments;
import cn.com.springtxdemo.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/20 15:25
 */
@Service
public class TxDemoService {

    @Autowired
    private DepartmentsMapper departmentsMapper;


    @Autowired
    private DepartmentsService departmentsService;

    @Transactional
    public void add() {
        Departments departments = new Departments();
        departments.setDepartmentName("张三");
        departments.setLocationId(1111111);
        departmentsMapper.insert(departments);

        new Thread(() -> {
            departmentsService.updateStatus();
        }).start();

    }


}
