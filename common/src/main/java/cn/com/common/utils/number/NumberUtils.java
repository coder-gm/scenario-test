package cn.com.common.utils.number;


/**
 * @program: cloud-terrace
 * @description: 数字处理工具
 * @author: gm.Zhang
 * @create: 2020-06-17 14:32
 **/
public class NumberUtils {


    /**
     * 判断字符串中是否全为数字
     * <p>
     * 解释:字符串中是否全为数字,数字中有小数点也不算是数字
     *
     * @param digits
     * @return
     */
    public static boolean isAllDigits(String digits) {
        return org.apache.commons.lang3.math.NumberUtils.isDigits(digits);
    }

    /**
     * 是否是有效数字
     *
     * @param digits
     * @return
     */
    public static boolean isDffectiveDigits(String digits) {
        return org.apache.commons.lang3.math.NumberUtils.isNumber(digits);
    }


}
