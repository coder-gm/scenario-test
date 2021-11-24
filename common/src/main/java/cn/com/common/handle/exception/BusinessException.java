package cn.com.common.handle.exception;

import java.io.Serializable;

/**
 * 通用业务异常,应用业务逻辑出错抛出此异常
 *
 * @program: cloud-terrace
 * @description: 业务异常
 * @author: gm.Zhang
 * @create: 2020-04-17 13:03
 **/
public class BusinessException extends BaseException {

    /**
     * 构造基础异常
     *
     * @param code           异常代码
     * @param args           异常参数
     * @param defaultMessage 默认异常消息
     * @param cause          引起此异常的出发异常
     */
    public BusinessException(String code, Serializable[] args, String defaultMessage, Throwable cause) {
        super(code, args, defaultMessage, cause);
    }

    /**
     * 构造基础异常
     *
     * @param code           异常代码
     * @param args           异常参数
     * @param defaultMessage 默认异常消息
     */
    public BusinessException(String code, Serializable[] args, String defaultMessage) {
        super(code, args, defaultMessage);
    }

    /**
     * 构造基础异常
     *
     * @param defaultMessage 默认异常消息
     * @param cause          引起此异常的出发异常
     */
    public BusinessException(String defaultMessage, Throwable cause) {
        super(defaultMessage, cause);
    }

    /**
     * 构造基础异常
     *
     * @param defaultMessage 默认异常消息
     */
    public BusinessException(String defaultMessage) {
        super(defaultMessage);
    }

    /**
     * 构造基础异常
     *
     * @param code  异常代码
     * @param args  异常参数
     * @param cause 引起此异常的出发异常
     */
    public BusinessException(String code, Serializable[] args, Throwable cause) {
        super(code, args, cause);
    }

    /**
     * 构造基础异常
     *
     * @param code 异常代码
     * @param args 异常参数
     */
    public BusinessException(String code, Serializable[] args) {
        super(code, args);
    }
}
