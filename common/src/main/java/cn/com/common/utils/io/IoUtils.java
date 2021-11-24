package cn.com.common.utils.io;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @version: V1.0
 * @author: zgming
 * @className: IoUtils
 * @packageName: cn.com.common.utils.io
 * @description: IO流工具类
 * @data: 2020/9/9 23:02
 **/
public class IoUtils {

    /**
     * url 转 InputStream
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    public static InputStream urlToInputStream(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        // 连接超时:30s
        conn.setConnectTimeout(30 * 1000);
        // IO超时:1min
        conn.setReadTimeout(1 * 1000 * 1000);
        conn.connect();

        // 创建输入流读取文件
        return conn.getInputStream();
    }


    /**
     * bast64 转 输入流
     *
     * @return
     */
    public static InputStream base64ToInputStream(String bast64) throws IOException {
        //将字符串转换为byte数组
        byte[] bytes = new BASE64Decoder().decodeBuffer(bast64.trim());
        //转化为输入流
        return new ByteArrayInputStream(bytes);
    }


}