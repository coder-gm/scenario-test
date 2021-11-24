package cn.com.common.handle.exception;

import java.io.Serializable;

/**
 * @program: cloud-terrace
 * @description: 定义基础异常
 * @author: gm.Zhang
 * @create: 2020-04-17 13:07
 **/
public class BaseException extends RuntimeException {

    /**
     * 异常代码
     */
    private final String code;

    /**
     * 错误码对应的参数
     */
    private final Serializable[] args;

    /**
     * 错误消息
     */
    private final String defaultMessage;

    /**
     * 构造基础异常
     *
     * @param code           异常代码
     * @param args           异常参数
     * @param defaultMessage 默认异常消息
     * @param cause          引起此异常的出发异常
     */
    public BaseException(String code, Serializable[] args, String defaultMessage, Throwable cause) {
        super(cause);
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    /**
     * 构造基础异常
     *
     * @param code           异常代码
     * @param args           异常参数
     * @param defaultMessage 默认异常消息
     */
    public BaseException(String code, Serializable[] args, String defaultMessage) {
        this(code, args, defaultMessage, null);
    }


    /**
     * 构造基础异常
     *
     * @param defaultMessage 默认异常消息
     * @param cause          引起此异常的出发异常
     */
    public BaseException(String defaultMessage, Throwable cause) {
        this(null, null, defaultMessage, cause);
    }


    /**
     * 构造基础异常
     *
     * @param defaultMessage 默认异常消息
     */
    public BaseException(String defaultMessage) {
        this(null, null, defaultMessage, null);
    }

    /**
     * 构造基础异常
     *
     * @param code  异常代码
     * @param args  异常参数
     * @param cause 引起此异常的出发异常
     */
    public BaseException(String code, Serializable[] args, Throwable cause) {
        this(code, args, null, cause);
    }


    /**
     * 构造基础异常
     *
     * @param code 异常代码
     * @param args 异常参数
     */
    public BaseException(String code, Serializable[] args) {
        this(code, args, null, null);
    }


    public String getCode() {
        return code;
    }


    public Object[] getArgs() {
        return args;
    }


    @Override
    public String getMessage() {
        return defaultMessage;
    }


    @Override
    public String toString() {
        return this.getClass() + "{" +
                ", message='" + getMessage() + '\'' +
                '}';
    }





}
