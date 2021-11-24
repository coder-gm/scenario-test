package cn.com.common.handle.logs;


import cn.com.common.model.response.ResponseData;
import cn.com.common.utils.ip.IpUtils;
import cn.com.common.utils.nulls.ObjectUtils;
import cn.com.common.utils.sql.IllegalStrFilterUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: zgm
 * @Description:调用接口打印性能日志以及接口报错之后记录错误日志 </p>
 * 现在项目里已经统一使用AOP方式来做全局异常统一处理了，
 * 选用AOP方式主要是因为AOP不只可以做全局异常统一处理还可以统一打印接口请求入参和返回结果日志，
 * 打印接口访问性能日志，处理sql注入攻击以及处理入参特殊字符等问题
 * @Date: 2018-11-29 23:34
 */
@Component
@Aspect
@Slf4j
public class GlobalLogHandle {


    /**
     * 环绕通知切入点
     **/
    @Pointcut("execution(* cn.com..controller.*.*(..))" +
            "&&!execution(* cn.com..controller.*.downloadTemplate(..))")
    public void aroundPointCut() {
    }


    /**
     * 环绕通知
     * <p>
     * 记录controller接口访问日志
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("aroundPointCut()")
    public ResponseData handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        //获取开始时间
        long startTime = System.currentTimeMillis();
        ResponseData responseData;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        String phoneNumber = request.getHeader("phoneNumber");
        String clientNo = request.getHeader("clientNo");
        String username = null;

        // 记录下请求内容
        log.info("***************************执行Controller开始***************************");
        log.info("请求地址 URL :{}", request.getRequestURL().toString());
        log.info("请求方式 :{}", request.getMethod());
        log.info("请求者IP地址 :{}", IpUtils.getIpAddr(request));
        log.info("访问的类与方法和入参 :{}", pjp.getSignature());

        //过滤掉request和response,不能序列化
        Object[] args = pjp.getArgs();
        List<Object> filteredArgs = Arrays.stream(args)
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        //获取参数名称
        LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
        String[] params = paramNames.getParameterNames(method);

        if (CollectionUtils.isEmpty(filteredArgs)) {
            log.info("请求参数 :{}", "");
        } else {
            //拼接请求参数
            Map<String, Object> rqsParams = IntStream.range(0, filteredArgs.size())
                    .boxed()
                    .collect(Collectors.toMap(j -> params[j], j -> ObjectUtils.isEmpty(filteredArgs.get(j)) ? "" : filteredArgs.get(j)));

            log.info("请求参数 :{}", rqsParams);
        }


        if (ObjectUtils.isEmpty(username)) {
            username = phoneNumber;
        }

        //处理入参特殊字符和sql注入攻击
        checkRequestParam(pjp);

        //执行访问接口操作(所有controller接口都会从这个AOP过滤一遍,然后返回responseData)
        responseData = (ResponseData) pjp.proceed(pjp.getArgs());

        try {
            log.info("***************************执行Controller结束***************************");
            log.info("返回值：{}", JSONObject.toJSONString(responseData));


            //获取结束时间
            long endTime = System.currentTimeMillis();
            long timeConsuming = endTime - startTime;
            log.info("耗时(毫秒)：{}", timeConsuming);


        } catch (Exception ex) {
            //此处将日志打印放入try-catch是因为项目中有些对象实体bean过于复杂，导致序列化为json的时候报错，但是此处报错并不影响主要功能使用，
            //只是返回结果日志没有打印，所以catch中也不做抛出异常处理
            log.error("访问的类与方法和入参:{}, 接口记录返回结果失败！，原因为：{}", pjp.getSignature(), ex);
        }

        return responseData;
    }


    /**
     * 处理入参特殊字符和sql注入攻击
     *
     * @param pjp
     */
    private void checkRequestParam(ProceedingJoinPoint pjp) {
        String str = String.valueOf(pjp.getArgs());
        if (!IllegalStrFilterUtils.sqlStrFilter(str)) {
            log.info("访问接口:{}，输入参数存在SQL注入风险！参数为:{}", pjp.getSignature(), JSONObject.toJSONString(pjp.getArgs()));
        }
        if (!IllegalStrFilterUtils.isIllegalStr(str)) {
            log.info("访问接口:{}，输入参数存在SQL注入风险！参数为:{}", pjp.getSignature(), JSONObject.toJSONString(pjp.getArgs()));
        }
    }


}



