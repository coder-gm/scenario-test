package cn.com.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: cloud-terrace
 * @description: 数字格式枚举
 * @author: gm.Zhang
 * @create: 2020-06-28 15:34
 **/
@Slf4j
@AllArgsConstructor
public enum NumberFormatEnum {

    /**
     * 千分之 符号
     */
    PER_MILL(1, "#\u2030"),
    NUM_FORMAT_1(2, "##.##"),
    NUM_FORMAT_2(3, "0000.00"),
    NUM_FORMAT_3(4, "##.##"),
    NUM_FORMAT_4(5, "#.##%"),
    NUM_FORMAT_5(6, "###,###.###"),
    NUM_FORMAT_6(7, "000,000.000"),
    NUM_FORMAT_7(8, "###,###.###$"),
    NUM_FORMAT_8(9, "000,000.000￥"),
    NUM_FORMAT_9(10, "###.###\u2030");

    @Getter
    private Integer code;

    @Getter
    private String format;
}
