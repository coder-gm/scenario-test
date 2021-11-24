package cn.com.common.utils.file;

import cn.com.common.handle.exception.BusinessException;
import cn.com.common.utils.excel.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 下载工具类
 *
 * @author zgming
 * @create 2020-11-16 15:02
 */
public final class DownloadUtils {


    /**
     * @param fileName
     * @param request
     * @param response
     */
    public static void downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) {
        XSSFWorkbook xssfWorkbook;
        InputStream inputStream = null;

        try {

            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload; charset=UTF-8");

            inputStream = DownloadUtils.class.getClassLoader().getResourceAsStream("template/" + fileName);
            setFileDownloadHeader(request, response, fileName);

            xssfWorkbook = (XSSFWorkbook) ExcelUtils.getWorkBook(fileName, inputStream);

            xssfWorkbook.write(response.getOutputStream());

            response.getOutputStream().flush();

        } catch (UnsupportedEncodingException e) {
            throw new BusinessException("下载出现异常:{}", e);
        } catch (IOException e) {
            throw new BusinessException("下载出现异常:{}", e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    /**
     * @param request
     * @param response
     * @param fileName
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String userAgent = request.getHeader("USER-AGENT");
        try {

            String finalfilename;

            if ("MSIE".equals(userAgent)) { //IE浏览器
                finalfilename = URLEncoder.encode(fileName, "UTF8");
            } else if ("Mozilla".equals(userAgent)) { //火狐浏览器
                finalfilename = new String(fileName.getBytes(), "ISO8859-1");
            } else {//其他浏览器
                finalfilename = URLEncoder.encode(fileName, "UTF8")
                        .replaceAll("\\+", "%20")
                        .replaceAll("%28", "\\(")
                        .replaceAll("%29", "\\)")
                        .replaceAll("%3B", ";")
                        .replaceAll("%40", "@")
                        .replaceAll("%23", "\\#")
                        .replaceAll("%26", "\\&")
                        .replaceAll("%2C", "\\,");
            }

            response.setHeader("Content-Disposition", "attachment;filename=\"" + finalfilename + "\"");
            //这里设置一下让浏览器弹出下载提示框,而不是直接在浏览器中打开
        } catch (UnsupportedEncodingException e) {

        }
    }


}
