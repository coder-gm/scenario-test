package cn.com.springtxdemo.service.impl;

import cn.com.springtxdemo.model.po.Departments;
import cn.com.springtxdemo.mapper.DepartmentsMapper;
import cn.com.springtxdemo.service.DepartmentsService;
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
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments> implements DepartmentsService {

}
