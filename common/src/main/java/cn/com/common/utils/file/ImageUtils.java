package cn.com.common.utils.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图片处理服务类
 *
 * @author guangming
 */
@Component
public class ImageUtils {

    @Value("${ftp.file.image.path}")
    private static String IMG_PATH;


    /**
     * IO流读取图片
     *
     * @param imgName 即图片保存在服务器上的名称
     */
    public static void ioReadImage(String imgName, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = IMG_PATH + imgName;
            ips = new FileInputStream(new File(imgPath));
            String type = imgName.substring(imgName.indexOf(".") + 1);
            if ("png".equals(type)) {
                response.setContentType("image/png");
            }
            if ("jpeg".equals(type)) {
                response.setContentType("image/jpeg");
            }
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            ips.close();
        }
    }


}
