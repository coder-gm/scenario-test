package cn.com.springtxdemo.service.impl;

import cn.com.springtxdemo.model.po.Departments;
import cn.com.springtxdemo.mapper.DepartmentsMapper;
import cn.com.springtxdemo.service.DepartmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zhang Guang Ming
 * @since 2021-12-18
 */
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments> implements DepartmentsService {


    @Override
    @Transactional
    public void add() {
        Departments departments = new Departments();
        departments.setDepartmentName("张三");
        departments.setLocationId(1111111);
        this.save(departments);
        updateStatus();
        int num = 1 / 0;
    }

    @Transactional
    public void updateStatus() {
        Departments departments = new Departments();
        departments.setDepartmentName("李四");
        departments.setLocationId(22222222);
        this.save(departments);
    }

}
