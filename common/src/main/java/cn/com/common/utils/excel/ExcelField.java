package cn.com.common.utils.excel;

import lombok.Data;

/**
 * @program: cloud-terrace
 * @description: excel表格表头
 * @author: gm.Zhang
 * @create: 2020-08-12 10:09
 **/
@Data
public class ExcelField {

    private String name;

    private String field;

    private int sort;

    private String cellStyleName;

    private ExcelFieldType excelFieldType;


}
