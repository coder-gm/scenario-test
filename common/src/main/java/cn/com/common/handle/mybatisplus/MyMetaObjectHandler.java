package cn.com.common.handle.mybatisplus;


import cn.com.common.utils.nulls.ObjectUtils;
import cn.com.common.utils.security.JwtUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 注入公共字段自动填充,任选注入方式即可
 *
 * @ProjectName: spring-boot-mybatisplus-oracle
 * @Package: com.springboot.mybatisplus.oracle.config
 * @ClassName: MyMetaObjectHandler
 * @Author: zgming
 * @Description: ${description}
 * @Date: 2019/5/22 11:43
 * @Version: 1.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 获取用户信息
     *
     * @return
     */
    public String getUserName() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!ObjectUtils.isEmpty(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            log.info("从Token中获取用户名:{}", token);
            if (!ObjectUtils.isEmpty(token)) {
                return JwtUtils.getUserName(token);
            }
        }

        return null;
    }


    /**
     * 插入操作
     * <p>
     * 版本号3.0.6以及之前的版本
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("(插入操作)从Token中获取用户名:{}", getUserName());

        // 起始版本 3.3.0(推荐使用)
        // 创建时间
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());

        //创建人
        this.strictInsertFill(metaObject, "createdUser", String.class, getUserName());
    }

    /**
     * 更新操作
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("(更新操作)从Token中获取用户名:{}", getUserName());
        // 起始版本 3.3.0(推荐使用)
        // 修改时间
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
        //更新人
        this.strictUpdateFill(metaObject, "updatedUser", String.class, getUserName());

    }
}
