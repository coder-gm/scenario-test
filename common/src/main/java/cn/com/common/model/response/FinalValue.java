package cn.com.common.model.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 光明
 * @date 2019-12-04 9:22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinalValue {
    /**
     * 成功返回码
     */
    public static final int CODE_OK = 1;
    /**
     * 不成功返回码
     */
    public static final int CODE_NO = 0;

    /**
     * msg 成功
     */
    public static final String MSG_SUCCESS = "success";

    /**
     * msg 成功
     */
    public static final String MSG_SUCCESSFUL = "删除成功";


    /**
     * msg 失败
     */
    public static final String MSG_FAILED = "AILED";

    /**
     * msg 失败
     */
    public static final String MSG_FAIL = "删除失败";


    /**
     * 统一请求编码KEY(用户日志追踪使用)
     */
    public static final String REQUEST_ID_KEY = "REQUEST_ID";

    /**
     * 不成功状态码
     */
    public static final int CODE_500 = 500;

    public static final Integer ZERO = 0;
    public static final Integer ONE = 1;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer FOUR = 4;
    public static final Integer FIVE = 5;
    public static final Integer SIX = 6;
    public static final Integer SEVEN = 7;
    public static final Integer EIGHT = 8;
    public static final Integer NINE = 9;

}
