package cn.com.common.utils.nulls;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据非空校验工具类
 *
 * @author 光明
 * @date 2020-01-16 9:56
 */
public class ObjectUtils {


    /**
     * 对象非空校验
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return "".equals(obj.toString().trim());
        } else if (obj instanceof List) {
            return ((List) obj).size() == 0;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0;
        } else if (obj instanceof Set) {
            return ((Set) obj).size() == 0;
        } else if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        } else if (obj instanceof int[]) {
            return ((int[]) obj).length == 0;
        } else if (obj instanceof long[]) {
            return ((long[]) obj).length == 0;
        }

        return false;
    }


//    /**
//     *  TODO:建议使用 isBlank
//     判断是否为空(注：isBlank与isEmpty 区别)
//     */
//    @Test
//    public void test16() {
//        //判断是否为空(注：isBlank与isEmpty 区别)
//        log.info("====111======>:{}", StringUtils.isBlank(null));//true
//        log.info("====111======>:{}", StringUtils.isEmpty(null));//true
//
//        log.info("====222======>:{}", StringUtils.isBlank(""));// true
//        log.info("====222======>:{}", StringUtils.isEmpty(""));// true
//
//        log.info("====333======>:{}", StringUtils.isBlank(" "));// true
//        log.info("====333======>:{}", StringUtils.isEmpty(" "));// false
//
//        log.info("====444======>:{}", StringUtils.isBlank("\t \n \f \r"));// true //对于制表符、换行符、换页符和回车符
//        log.info("====444======>:{}", StringUtils.isEmpty("\t \n \f \r"));// false //对于制表符、换行符、换页符和回车符
//
//        //log.info("====4444======>:{}", StringUtils.isBlank();//均识为空白符
//
//        log.info("====555======>:{}", StringUtils.isBlank("\b"));// false //”\b”为单词边界符
//        log.info("====555======>:{}", StringUtils.isEmpty("\b"));// false //”\b”为单词边界符
//
//        log.info("====666======>:{}", StringUtils.isBlank("bob"));// false
//        log.info("====666======>:{}", StringUtils.isEmpty("bob"));// false
//
//        log.info("====777======>:{}", StringUtils.isBlank(" bob "));// false
//        log.info("====777======>:{}", StringUtils.isEmpty(" bob "));// false
//
//        //参数全不为空返回true, 否则false;
//        log.info("====888======>:{}", StringUtils.isNoneBlank(" ", "bar"));//---false
//        log.info("====888======>:{}", StringUtils.isNoneEmpty(" ", "bar"));//---true
//    }
//


}
