package cn.com.common.utils.excel;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * Excel 样式
 *
 * @author zhangguangming
 * @create 2020-02-29-22:35-
 */
public class ExcelStyleUtils {

    /**
     * 文本居中,自动换行
     *
     * @param xSSFCellStyle
     * @return
     */
    public static XSSFCellStyle textCenter(XSSFCellStyle xSSFCellStyle) {
        //垂直居中
        xSSFCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //水平居中
        xSSFCellStyle.setAlignment(HorizontalAlignment.CENTER);
        //换行
        xSSFCellStyle.setWrapText(true);
        return xSSFCellStyle;
    }

    /**
     * 文本靠左,垂直居中,自动换行
     *
     * @param xSSFCellStyle
     * @return
     */
    public static XSSFCellStyle textLeft(XSSFCellStyle xSSFCellStyle) {
        //水平居中
        xSSFCellStyle.setAlignment(HorizontalAlignment.LEFT);
        //垂直居中
        xSSFCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //换行
        xSSFCellStyle.setWrapText(true);
        return xSSFCellStyle;
    }




    /**
     * 字体设置
     *
     * @param font
     * @return
     */
    public static XSSFCellStyle textFont(XSSFCellStyle xssfCellStyle, Font font, short size) {
        //font.setItalic(true);                     // 设置字体为斜体字
        //font.setColor(Font.COLOR_RED);            // 将字体设置为“红色”
        font.setFontHeightInPoints(size);    // 将字体大小设置为18px
        //font.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上
        //font.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）
        font.setBold(true); //字体加粗
        xssfCellStyle.setFont(font);
        return xssfCellStyle;
    }


    /**
     * 设置单元格边框
     *
     * @param xssfCellStyle
     * @return
     */
    public static XSSFCellStyle setBorder(XSSFCellStyle xssfCellStyle, short borderVal, short colorVal) {
        //边框类型 ：dished-虚线 thick-加粗 double-双重 dotted-有点的 CellStyle.BORDER_THICK
        //设置底部格式（样式+颜色）
        xssfCellStyle.setBorderBottom(BorderStyle.valueOf((short) 1));
        //style.setBottomBorderColor(borderColor);
        //设置左边格式
        xssfCellStyle.setBorderLeft(BorderStyle.valueOf((short) 1));
        //style.setLeftBorderColor(borderColor);
        //设置右边格式
        xssfCellStyle.setBorderRight(BorderStyle.valueOf((short) 1));
        //style.setRightBorderColor(borderColor);
        //设置顶部格式
        xssfCellStyle.setBorderTop(BorderStyle.valueOf((short) 1));
        //xssfCellStyle.setTopBorderColor(IndexedColors.BLACK1.index);

        return xssfCellStyle;
    }


}
