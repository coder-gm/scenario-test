package cn.com.common.handle.exception;

import cn.com.common.model.response.FinalValue;
import cn.com.common.model.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author zhanggm
 * @create 2020-04-2020/4/17-11:59
 */
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandle {


    /**
     * 通用业务异常,应用业务逻辑出错抛出此异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseData HandleBusinessException(BusinessException ex) {
        ResponseData result = new ResponseData();
        result.setRet(FinalValue.CODE_NO);
        result.setMsg(ex.getMessage());
        result.setRequestId(MDC.get(FinalValue.REQUEST_ID_KEY));
        log.error("通用业务异常,应用业务逻辑出错抛出此异常:{}", ex);
        return result;
    }

    /**
     * 任意异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData HandleAllException(Exception ex) {
        ResponseData result = new ResponseData();
        if (ex instanceof SQLIntegrityConstraintViolationException) {
            result.setRet(FinalValue.CODE_500);
            result.setMsg("操作失败,已违反约束(外键、主键或唯一键)!!");
        } else if (ex instanceof IOException) {
            result.setRet(FinalValue.CODE_500);
            result.setMsg("操作失败,请检查操作文件!!");
        } else if (ex instanceof DuplicateKeyException) {
            result.setRet(FinalValue.CODE_NO);
            result.setMsg("当前数据已存在,新增失败!!");
        } else if (ex instanceof MaxUploadSizeExceededException) {
            result.setRet(FinalValue.CODE_500);
            result.setMsg("上传文件过大,请检查操作文件!!");
        } else {
            result.setRet(FinalValue.CODE_500);
            result.setMsg(ex.getMessage());
        }

        ex.printStackTrace();
        String requestId = MDC.get(FinalValue.REQUEST_ID_KEY);
        result.setRequestId(requestId);
        log.error("任意异常:{},请求ID:{}", ex, requestId);
        return result;
    }


    /**
     * 请求参数异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseData HandleFieldValidateException(BindException ex) {
        ResponseData result = new ResponseData();
        result.setRet(400001);
        result.setMsg(ex.getFieldError().getDefaultMessage());
        log.error("请求参数异常:{}", ex);
        return result;
    }


    /**
     * 请求参数异常处理(方法)
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseData HandleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseData result = new ResponseData();
        result.setRet(400001);
        result.setMsg(ex.getBindingResult().getFieldError().getDefaultMessage());
        log.error("请求参数异常:{}", ex);
        return result;
    }

}
