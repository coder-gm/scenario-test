package cn.com.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: cloud-terrace
 * @description: 日期格式枚举
 * @author: gm.Zhang
 * @create: 2020-06-28 15:34
 **/
@Slf4j
@AllArgsConstructor
public enum DateFormatEnum {

    FORMAT(1, "yyyy-MM-dd HH:mm:ss"),
    FORMAT_DAY(2, "yyyy-MM-dd"),
    HOUR_MINUTE_SECONDS(3, "HH:mm:ss"),
    HOUR_MINUTE_SECONDS_MS(4, "HH:mm:ss:SSS"),
    FORMAT_DAY_HOUR_MINUTE(5, "yyyy-MM-dd HH:mm"),
    FORMAT_DAY_CHINA(6, "yyyy年MM月dd日"),
    FORMAT_CHINA(7, "yyyy年MM月dd日HH时mm分ss秒"),
    FORMAT_DAY_1(8, "yyyyMMdd"),
    FORMAT_DAY_2(9, "yyyyMM");
    @Getter
    private Integer code;

    @Getter
    private String format;
}
