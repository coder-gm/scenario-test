package cn.com.common.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 菜单状态枚举
 *
 * @author zgming
 * @create 2020-11-25 16:26
 */
@Getter
public enum StatusEnum {

    PAUSE("0", "暂停"),
    ENABLE("1", "启用");

    StatusEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }


    /**
     * 标记数据库存的值是code
     * Integer 这个字段 一定要对应上数据库的字段（驼峰转换）
     */
    @EnumValue
    private final String code;

    /**
     * 两个注解一定要加
     */
    @JsonValue
    private final String value;

    /**
     * 一定要重写toString()
     *
     * @return
     */
    @Override
    public String toString() {
        return this.value;
    }
}