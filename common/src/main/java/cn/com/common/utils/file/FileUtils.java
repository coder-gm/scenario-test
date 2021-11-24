package cn.com.common.utils.file;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author guangming
 */
@Slf4j
@Service
public class FileUtils {
    /**
     * linux存放文件路径
     */
    @Value("${ftp.file.image.path}")
    private String ftpFileImagePath;
    /**
     * 图片格式 ,JPG JPEG GIF PNG BMP
     */
    @Value("${file.type.image}")
    private static String fileTypeImage;
    /**
     * 文档格式 doc,text
     */
    @Value("${file.type.text}")
    private static String fileTypeText;
    /**
     * excel格式  xlsx ,xls
     */
    @Value("${file.type.excel}")
    private static String fileTypeExcel;


    /**
     * 文件上传(多文件)
     *
     * @param files      上传的文件
     * @param formatType 上传的格式类型
     *                   1:图片格式 ,JPG JPEG GIF PNG BMP
     *                   2:文档格式 doc,text
     *                   3:excel格式  xlsx ,xls
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> uploadFiles(MultipartFile[] files, String path, String formatType) throws Exception {
        List<Map<String, String>> listEntity = new ArrayList<>();

        //1:图片格式 ,JPG JPEG GIF PNG BMP
        if (fileTypeImage.equals(formatType)) {
            for (MultipartFile file : files) {
                // 文件后缀
                String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                if ("jpg".equals(suffix) || "jpeg".equals(suffix) || "gif".equals(suffix) || "png".equals(suffix) || "bmp".equals(suffix)) {
                    listEntity.add(uploadMultipartFile(file, path));
                }
            }
        }
        //2:文档格式 doc,text
        if (fileTypeText.equals(formatType)) {
            for (MultipartFile file : files) {
                // 文件后缀
                String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                if ("doc".equals(suffix) || "text".equals(suffix)) {
                    listEntity.add(uploadMultipartFile(file, path));
                }
            }
        }
        // 3:excel格式  xlsx ,xls
        if (fileTypeExcel.equals(formatType)) {
            for (MultipartFile file : files) {
                // 文件后缀
                String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                if ("xlsx".equals(suffix) || "xls".equals(suffix)) {
                    listEntity.add(uploadMultipartFile(file, path));
                }
            }
        }
        return listEntity;
    }


    /**
     * 单文件上传
     *
     * @param file
     * @param path
     * @return
     * @throws Exception
     */
    public static Map<String, String> uploadMultipartFile(MultipartFile file, String path) throws Exception {
        Map<String, String> map = new HashMap<>();
        log.info("开始上传文件");
        // 获取图片原来的名字
        String oldFileName = file.getOriginalFilename();
        // 通过工具类产生新图片名称，防止重名
        String newFileName = generateRandonFileName(oldFileName);
        // 文件后缀
        String suffix = Objects.requireNonNull(oldFileName).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        File outFle = new File(path.concat(newFileName));
        //把文件放到outFle文件夹下
        file.transferTo(outFle);
        StringBuilder result = new StringBuilder(path).append(newFileName);
        log.info("文件地址为:{}", result);
        //用户ID
        map.put("userId", "123456");
        //用户名称
        map.put("userName", "明仔");
        //修改后文件名
        map.put("fileName", newFileName);
        //文件后缀
        map.put("fileSuffix", suffix);
        //原文件名称
        map.put("sourceFileName", oldFileName);
        //文件地址
        map.put("filePath", result.toString());

        return map;
    }


//    /**
//     * 保存数据到数据库
//     *
//     * @param fileInfoDTO
//     * @return
//     */
//    private static FileInfo saveFileDb(FileInfoDTO fileInfoDTO) {
//        log.info("开始保存文件到数据库");
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setUserId(fileInfoDTO.getUserId());//用户ID
//        fileInfo.setUserName(fileInfoDTO.getUserName());//用户名称
//        fileInfo.setFileName(fileInfoDTO.getFileName());//修改后文件名
//        fileInfo.setFileSuffix(fileInfoDTO.getFileSuffix());//文件后缀
//        fileInfo.setSourceFileName(fileInfoDTO.getSourceFileName());//原文件名称
//        fileInfo.setFilePath(fileInfoDTO.getFilePath());//文件地址
//        fileInfoService.save(fileInfo);
//        log.info("保存文件到数据库成功！fileInfo:{}", fileInfo);
//        return fileInfo;
//    }


    /**
     * 获得随机UUID文件名
     *
     * @param fileName
     * @return
     */
    public static String generateRandonFileName(String fileName) {
        // 首相获得扩展名，然后生成一个UUID码作为名称，然后加上扩展名
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + ext;
    }

    /**
     * 获得随机UUID文件名
     *
     * @return
     */
    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }


    /**
     * 文件下载
     *
     * @param fileName
     * @param response
     * @return
     */
    public static String downloadFile(String fileName, String filePath, HttpServletResponse response) {
        //String downloadFilePath = ftpFilePath + fileName;//被下载的文件在服务器中的路径,
        File file = new File(filePath);
        if (file.exists()) {
            // 设置强制下载不打开  
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }

                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "下载失败";
    }

}
