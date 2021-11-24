package cn.com.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zgming
 * @Description:友好提示枚举
 * @Date: 2018-12-03 11:12
 */
@AllArgsConstructor
public enum ResponseEnums {

    SYSTEM_ERROR("0002", "系统异常"),
    BAD_REQUEST("0003", "错误的请求参数"),
    NOT_FOUND("0004", "找不到请求路径！"),
    CONNECTION_ERROR("0005", "网络连接请求失败！"),
    METHOD_NOT_ALLOWED("0006", "不合法的请求方式"),
    FAILURE_OPTION("0007", "操作失败"),
    DATABASE_ERROR("0008", "数据库异常"),
    CUSTOM_ERROR("0009", "自定义异常"),
    BOUND_STATEMENT_NOT_FOUNT("0010", "找不到方法！"),
    REPEAT_REGISTER("0011", "重复注册"),
    NO_USER_EXIST("0012", "用户不存在"),
    INVALID_PASSWORD("0013", "密码错误"),
    NO_PERMISSION("0014", "非法请求！"),
    SUCCESS_OPTION("0015", "操作成功！"),
    NOT_MATCH("0016", "用户名和密码不匹配"),
    INVALID_MOBILE("0017", "无效的手机号码"),
    INVALID_EMAIL("0018", "无效的邮箱"),
    INVALID_GENDER("0019", "无效的性别"),
    REPEAT_MOBILE("0020", "已存在此手机号"),
    REPEAT_EMAIL("0021", "已存在此邮箱地址"),
    NO_RECORD("0022", "没有查到相关记录"),
    LOGIN_SUCCESS("0023", "登陆成功"),
    LOGOUT_SUCCESS("0024", "注销成功"),
    No_FileSELECT("0025", "未选择文件"),
    FILEUPLOAD_SUCCESS("0026", "上传成功"),
    NO_LOGIN("0027", "未登陆"),
    ILLEGAL_ARGUMENT("0028", "参数不合法"),
    VERIFY_CODE_ERROR("0029", "验证码不正确"),
    NO_AUTHORITY_URI("0030", "链接未授权"),
    REGISTERED_SUCCESS("0031", "注册成功"),
    REGISTERED_FAIL("0032", "注册失败"),
    USER_EXISTING("0033", "用户已存在"),
    NOT_AUTH_ACCESS("0034", "无权限访问"),
    NOT_LOGIN("0035", "未登录"),
    RUNTIME_ERROR("0036", "运行异常");

    @Getter
    private static final Map<String, String> ENUM_MAP = new HashMap<>();

    static {
        for (ResponseEnums responseEnums : EnumSet.allOf(ResponseEnums.class)) {
            ENUM_MAP.put(responseEnums.getCode(), responseEnums.getMsg());
        }
    }


    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String msg;

}
