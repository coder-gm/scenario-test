package cn.com.common.utils.date;

import lombok.Data;

/**
 * @program: cloud-terrace
 * @description: 日期时间实体类
 * @author: gm.Zhang
 * @create: 2020-06-28 18:23
 **/
@Data
public class DateTimeModel {
    /**
     * 年
     */
    private Integer year;
    /**
     * 月
     */
    private Integer month;
    /**
     * 天
     */
    private Integer day;
    /**
     * 小时
     */
    private Integer hour;
    /**
     * 分钟
     */
    private Integer minute;
    /**
     * 秒
     */
    private Integer second;
    /**
     * 纳秒
     */
    private Integer nanos;


}
