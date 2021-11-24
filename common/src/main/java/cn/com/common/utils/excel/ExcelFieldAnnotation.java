package cn.com.common.utils.excel;

import java.lang.annotation.*;

/**
 * @author zhangguangming
 * @create 2020-02-28-21:25-
 */


@Documented
//注解应用类型
@Target({ElementType.FIELD})
//注解的类型
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFieldAnnotation {

    String name();

    int sort();

    String cellStyleName() default "default_border";

    ExcelFieldType type() default ExcelFieldType.AUTO;

}
