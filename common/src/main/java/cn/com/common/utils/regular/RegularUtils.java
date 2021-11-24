package cn.com.common.utils.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式校验
 *
 * @author dongtangqiang
 * @create 2020-04-2020/4/4-17:19
 */
public class RegularUtils {

//    固话电话+手机号码-正则表达式
//    区号+座机号码+分机号码：
//    regExp="^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,6})?$"
//    简化
//    regExp="^(0\d{2,3}\-)?([2-9]\d{6,7})+(\-\d{1,6})?$"
//
//    手机号：
//    regExp="^(((\+86)|(\+86-))|((86)|(86\-))|((0086)|(0086\-)))?1[3|5|7|8]\d{9}$"
//    简化
//    regExp="^((\+86|\+86\-)|(86|86\-)|(0086|0086\-))?1[3|5|7|8]\d{9}$"
//


    /**
     * 验证手机号
     */
    private static Pattern CHECK_MOBILE = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
    /**
     * 验证带区号的
     */
    private static Pattern CHECK_PHONE_AREA_CODE = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
    /**
     * 验证没有区号的
     */
    private static Pattern CHECK_PHONE = Pattern.compile("^[1-9][0-9]{5,8}$");

    /**
     * 验证没有区号的
     */
    private static Pattern CHECK_PHONE_86 = Pattern.compile("^((\\+86)|(86))?[1][3456789][0-9]{9}$");

    /**
     * 正则表达式（国际固定电话和手机号）
     */
    private static Pattern CHECK_INTER_CALL = Pattern.compile("^(((\\+\\d{2}-)?0\\d{2,3}-\\d{7,8})|((\\+\\d{2}-)?(\\d{2,3}-)?([1][3,4,5,7,8,9][0-9]\\d{8})))$");

    /**
     * 验证是否是数字
     */
    private static Pattern IS_NUMBER = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    /**
     * 非负数
     */
    private static Pattern NONNEGATIVE_NUMBER = Pattern.compile("^\\d+$");

    /**
     * 正整数
     */
    private static Pattern POSITIVE_INTEGER = Pattern.compile("^[1-9]|[1-9][0-9]*$");

    /**
     * 非正整数
     */
    private static Pattern NON_POSITIVE_INTEGER = Pattern.compile("((-\\d+)|0)");

    /**
     * 负整数
     */
    private static Pattern NEGATIVE_INTEGER = Pattern.compile("^-([1-9]|[1-9][0-9])*$");

    /**
     * 整数
     */
    private static Pattern INTEGER = Pattern.compile("^-?\\d+$");

    /**
     * 非负浮点数
     */
    private static Pattern NON_NEGATIVE_FLOATING_POINT = Pattern.compile("^\\d+(\\.\\d+)?$");

    /**
     * 非负浮点数,可保留一位，二位，三位小数
     */
    private static Pattern NON_NEGATIVE_FLOATING_POINT3 = Pattern.compile("^\\d+(\\.\\d{1,3})?$");

    /**
     * 正浮点数
     */
    private static Pattern POSITIVE_FLOATING_POINT = Pattern.compile("^([1-9]+(\\.\\d+)?|0\\.\\d+)$");

    /**
     * 非正浮点数
     */
    private static Pattern NON_POSITIVE_FLOATING_POINT = Pattern.compile("^-[0-9]+(\\.\\d+)?|0$");

    /**
     * 负浮点数
     */
    private static Pattern NEGATIVE_FLOATING_POINT = Pattern.compile("^-[0-9]+(\\.\\d+)?$");

    /**
     * 浮点数
     */
    private static Pattern FLOATING_POINT = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Matcher mobile = CHECK_MOBILE.matcher(str);
        return mobile.matches();
    }


    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        // 验证带区号的
        if (str.length() > 9) {
            Matcher phone = CHECK_PHONE_AREA_CODE.matcher(str);
            return phone.matches();
        } else { // 验证没有区号的
            Matcher phone = CHECK_PHONE.matcher(str);
            return phone.matches();
        }
    }


    /**
     * 支持格式示例-固话：+86-010-40020021，010-40020020 国家代码选填；
     * 手机： +86-13523458056 ，10-13523458056 ，13523458056 国家代码和区号选填
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean checkInterCall(final String str) {
        // 验证带区号的
        Matcher phone = CHECK_INTER_CALL.matcher(str);
        return phone.matches();
    }




    /**
     * 验证是否是数字
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isNumber(final String str) {
        Matcher mobile = IS_NUMBER.matcher(str);
        return mobile.matches();
    }



    /**
     * 正整数
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean positiveInteger(final String str) {
        Matcher mobile = POSITIVE_INTEGER.matcher(str);
        return mobile.matches();
    }


    public static void main(String[] args) {
        System.out.println( checkInterCall("19991287311"));
    }

}
