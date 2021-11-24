package cn.com.common.utils.http;

import cn.com.common.utils.nulls.ObjectUtils;
import cn.com.common.utils.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/9/30 22:40
 */
@Slf4j
public class HttpServletRequestUtils {

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getUserName() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        log.info("从Token中获取用户名:{}", token);
        if (!ObjectUtils.isEmpty(token)) {
            return JwtUtils.getUserName(token);
        }
        return null;
    }

}
