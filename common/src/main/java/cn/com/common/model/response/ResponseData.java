package cn.com.common.model.response;

import lombok.*;
import org.springframework.util.StringUtils;

/**
 * @author 光明
 * @date 2019-12-04 9:18
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    /**
     * 返回代码 0-成功
     */
    private int ret;

    /**
     * 返回具体信息
     */
    private String msg;

    /**
     * 0:代表没有数据 , 1:代表有数据
     */
    private int status;

    /**
     * 对象
     */
    private T data;

    /**
     * 统一请求编码KEY(用户日志追踪使用)
     */
    private String requestId;


    /**
     * 返回当前实体
     *
     * @return
     */
    public boolean isOk() {
        return FinalValue.CODE_OK == this.ret;
    }


    /**
     * 返回成功实体
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseData ok(T data) {
        ResponseData result = new ResponseData();
        result.setRet(FinalValue.CODE_OK);
        result.setMsg(FinalValue.MSG_SUCCESS);
        if (StringUtils.isEmpty(data)) {
            result.setStatus(0);
        } else {
            result.setData(data);
            result.setStatus(1);
        }
        return result;
    }


    /**
     * 返回成功消息
     *
     * @param msg
     * @return
     */
    public static ResponseData ok(String msg) {
        ResponseData result = new ResponseData();
        result.setRet(FinalValue.CODE_OK);
        result.setMsg(msg);
        result.setStatus(0);
        result.setData(null);
        return result;
    }


    /**
     * 返回成功消息,实体
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseData ok(String msg, T data) {
        ResponseData result = new ResponseData();
        result.setRet(FinalValue.CODE_OK);
        result.setMsg(msg);
        if (StringUtils.isEmpty(data)) {
            result.setStatus(0);
        } else {
            result.setStatus(1);
        }
        result.setData(data);
        return result;
    }


    /**
     * 返回错误码与错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static ResponseData error(int code, String msg) {
        ResponseData result = new ResponseData();
        result.setRet(code);
        result.setMsg(msg);
        result.setStatus(0);
        result.setData(null);
        return result;
    }


    /**
     * 返回错误码与错误信息
     *
     * @param msg
     * @return
     */
    public static ResponseData error(String msg) {
        ResponseData result = new ResponseData();
        result.setRet(FinalValue.CODE_NO);
        result.setMsg(msg);
        result.setStatus(0);
        result.setData(null);
        return result;
    }


    /**
     * 根据code返回错误信息
     *
     * @param code
     * @return
     */
    public static ResponseData error(int code) {
        ResponseData result = new ResponseData();
        result.setRet(code);
        result.setMsg(ResponseEnums.getENUM_MAP().get(code));
        result.setStatus(0);
        result.setData(null);
        return result;
    }


    /**
     * 数据未找到
     *
     * @return
     */
    public static ResponseData dataNotFund() {
        ResponseData result = new ResponseData();
        result.setRet(4000);
        result.setMsg("数据未找到");
        result.setStatus(0);
        result.setData(null);
        return result;
    }


    /**
     * @return
     */
    public static ResponseData error(ResponseEnums responseEnums) {
        return error(Integer.valueOf(responseEnums.getCode()), responseEnums.getMsg());
    }


}
