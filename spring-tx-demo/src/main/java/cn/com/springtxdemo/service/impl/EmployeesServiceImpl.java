package cn.com.springtxdemo.service.impl;

import cn.com.springtxdemo.model.po.Employees;
import cn.com.springtxdemo.mapper.EmployeesMapper;
import cn.com.springtxdemo.service.EmployeesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zhang Guang Ming
 * @since 2021-12-18
 */
@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees> implements EmployeesService {

}
