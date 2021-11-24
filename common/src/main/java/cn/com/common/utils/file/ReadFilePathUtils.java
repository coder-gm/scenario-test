package cn.com.common.utils.file;

import cn.com.common.handle.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取指定文件夹下的所有文件路径
 *
 * @author zgming
 */
@Slf4j
public class ReadFilePathUtils {

    /**
     * 读取某个文件夹下的所有文件
     *
     * @param filepath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<String> readFile(String filepath) {
        List<String> filePathList = new ArrayList();

        File file = new File(filepath);
        if (!file.isDirectory()) {
            System.out.println("文件");
            filePathList.add(file.getName());
        } else if (file.isDirectory()) {
            System.out.println("文件夹");
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "\\" + filelist[i]);
                if (!readfile.isDirectory()) {
                    filePathList.add(readfile.getName());
                } else if (readfile.isDirectory()) {
                    readFile(filepath + "\\" + filelist[i]);
                }
            }
        }
        return filePathList;
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean deleteFile(String delPath) throws FileNotFoundException, IOException {
        try {
            File file = new File(delPath);
            if (!file.isDirectory()) {
                System.out.println("1");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                String[] fileList = file.list();
                for (int i = 0; i < fileList.length; i++) {
                    File delFile = new File(delPath + "\\" + fileList[i]);
                    if (!delFile.isDirectory()) {
                        delFile.delete();
                        log.info("删除文件成功!!");
                    } else if (delFile.isDirectory()) {
                        deleteFile(delPath + "\\" + fileList[i]);
                    }
                }
                file.delete();
            }

        } catch (FileNotFoundException e) {
            throw new BusinessException("删除某个文件夹下的所有文件夹和文件异常!!", e);
        }
        return true;
    }


}
