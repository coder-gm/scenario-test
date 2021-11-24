package cn.com.common.utils.security;

import cn.com.common.handle.exception.BusinessException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @version: V1.0
 * @author: zgming
 * @className: MD5Utils
 * @packageName: cn.com.common.utils.auth
 * @description: MD5加密
 * @data: 2020/9/25 15:45
 **/
public class MD5Utils {

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("没有md5这个算法！");
        }

        // 16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;

    }
}