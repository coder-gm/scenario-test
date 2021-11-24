package cn.com.common.utils.excel;

import cn.com.common.handle.exception.BusinessException;
import cn.com.common.utils.nulls.ObjectUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出excel 处理工具类
 *
 * @author zhangguangming
 * @create 2020-02-28-23:06-
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtils {

    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";

    /**
     * 对文件流输出下载的中文文件进行编码,屏蔽各种浏览器版本的差异性
     *
     * @param httpServletRequest
     * @param pFileName
     * @return
     */
    public static String encodeChineseDownloadFileName(HttpServletRequest httpServletRequest, String pFileName) throws UnsupportedEncodingException {
        String fileName = null;
        String agent = httpServletRequest.getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {
                fileName = "?UTF-8?B?" + (new String(Base64.encodeBase64(pFileName.getBytes(StandardCharsets.UTF_8)))) + "?=";
            } else if (-1 != agent.indexOf("Chrome")) {
                //fileName = new String(pFileName.getBytes(StandardCharsets.ISO_8859_1));
                fileName = URLEncoder.encode(pFileName, "utf-8");
            } else {
                fileName = URLEncoder.encode(pFileName, StandardCharsets.UTF_8.name());
                fileName = fileName.replace("+", "%20");
            }
        } else {
            fileName = pFileName;
        }

        return fileName;
    }


    /**
     * 获取实体类 注解 ExcelFieldAnnotation 信息
     *
     * @return
     */
    public static Map<Integer, String> getExcelFieldAnnotationInfo1(Class<?> targetClass) {
        Map<Integer, String> map = new HashMap<>();
        //获取实体类中的所有字段
        Field[] fields = targetClass.getDeclaredFields();

        for (Field field : fields) {
            //获取有注解@ExcelFieldAnnotation的信息
            ExcelFieldAnnotation excelFieldAnnotation = field.getAnnotation(ExcelFieldAnnotation.class);
            if (null == excelFieldAnnotation) {
                continue;
            }
            int sort = excelFieldAnnotation.sort();
            String name = excelFieldAnnotation.name();
            map.put(sort, name);
        }

        return map;
    }


    /**
     * 获取实体类 注解 ExcelFieldAnnotation 信息
     *
     * @return
     */
    public static Map<Integer, ExcelField> getExcelFieldAnnotationInfo(Class<?> targetClass) {
        Map<Integer, ExcelField> map = new HashMap<>();
        //获取实体类中的所有字段
        Field[] fields = targetClass.getDeclaredFields();

        for (Field field : fields) {
            //获取有注解@ExcelFieldAnnotation的信息
            ExcelFieldAnnotation excelFieldAnnotation = field.getAnnotation(ExcelFieldAnnotation.class);
            if (null == excelFieldAnnotation) {
                continue;
            }
            int sort = excelFieldAnnotation.sort();
            ExcelField excelField = new ExcelField();
            excelField.setName(excelFieldAnnotation.name());
            excelField.setField(field.getName());
            excelField.setSort(sort);
            excelField.setCellStyleName(excelFieldAnnotation.cellStyleName());
            excelField.setExcelFieldType(excelFieldAnnotation.type());
            map.put(sort, excelField);
        }
        return map;
    }


    /**
     * 导入Excel数据
     *
     * @Author: zgming
     * @Param: file
     * @Return java.util.List<java.lang.String>
     * @Throws:
     **/
    public static List<List<String>> importExcel(MultipartFile file) {
        //获取文件名称
        String fileName = file.getOriginalFilename();
        log.info("导入解析开始，fileName:{}", fileName);

        long size = file.getSize();
        if (ObjectUtils.isEmpty(fileName) || size == 0) {
            throw new BusinessException("文件为空!!");
        }
        Workbook workbook = null;

        try {
            if (fileName.endsWith(SUFFIX_2003)) {
                workbook = WorkbookFactory.create(file.getInputStream());
            } else if (fileName.endsWith(SUFFIX_2007)) {
                workbook = WorkbookFactory.create(file.getInputStream());
            } else {
                throw new BusinessException("暂不支持该格式文件的导入!!");
            }
        } catch (IOException e) {
            throw new BusinessException("导入文件格式异常", e);
        }


        try {

            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();


            List<List<String>> list2Str = new ArrayList<>();

            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                //总列数
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                List<String> list = new ArrayList<>();
                for (Cell cell : row) {
                    String value = null;
                    switch (cell.getCellType()) {
                        case STRING:
                            value = cell.getStringCellValue();
                            break;
                        case BOOLEAN://布尔型
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case NUMERIC: //数值型
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                //日期
                                value = String.valueOf(cell.getDateCellValue().getTime());
                            } else {
                                value = BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros().toPlainString();
                            }
                            break;
                        case FORMULA: //公式
//                            NumberFormat nf = NumberFormat.getInstance();
//                            nf.setGroupingUsed(false);
//                            value = String.valueOf(nf.format(cell.getNumericCellValue()));
                            value = BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros().toPlainString();
                            break;
                        case BLANK: //空值
                            value = null;
                            break;
                        case ERROR: //错误
                            value = String.valueOf(cell.getErrorCellValue());
                            break;
                    }

                    list.add(value);
                }

                //判断某一行的所有元素都为空
                boolean isNotNull = list.stream().allMatch((e) -> ObjectUtils.isEmpty(e));

                if (!isNotNull){
                    list2Str.add(list);
                }

            }
            return list2Str;

        } catch (Exception e) {
            log.info("导入Excel数据异常:{}", e);
            throw new BusinessException("导入Excel数据异常:" + e.getMessage());
        }

    }

    /**
     * 对导出excel表格格式进行处理
     *
     * @param response
     * @param fileName
     * @Method exportDeal
     * @Author zgming
     * @Version 1.0
     * @Description
     * @Return void
     * @Exception
     * @Date 2019/6/18 16:09
     */
    public static void exportDeal(HttpServletResponse response, String fileName) {
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        response.setHeader("Pragma", "No-cache");
        response.setContentType("application/json;charset=UTF-8");
    }


    /**
     * 向excel中插入图片
     * <p>
     * <p>
     * 起始列,起始行,结束列,结束行
     *
     * @param xssfWorkbook
     * @param xSSFSheet
     * @throws IOException
     */
    public static void insertPicture(InputStream inputStream, XSSFWorkbook xssfWorkbook, XSSFSheet xSSFSheet, Integer rol1, Integer row1, Integer rol2, Integer row2) throws IOException {
        //byte 数组
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        // 图片文件
        BufferedImage bufferImg = ImageIO.read(inputStream);
        //图片写入byte数组
        ImageIO.write(bufferImg, "jpg", byteArrayOut);
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        XSSFDrawing xSSFDrawing = xSSFSheet.createDrawingPatriarch();
        //位置  后四个参数:   前两个: 图片左上角的X,Y坐标     后两个:图片右下左上角的X,Y坐标(xssfClientAnchor主要用于设置图片的属性)
        XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(0, 0, 0, 0, rol1, row1, rol2, row2);
        xssfClientAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
        ////插入图片
        xSSFDrawing.createPicture(xssfClientAnchor, xssfWorkbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
    }


    /**
     * @param fiileName
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static Workbook getWorkBook(String fiileName, InputStream inputStream) throws IOException {
        Workbook workbook = null;

        if (fiileName.endsWith(SUFFIX_2003)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fiileName.endsWith(SUFFIX_2007)) {
            workbook = new XSSFWorkbook(inputStream);
        }

        return workbook;
    }


}
