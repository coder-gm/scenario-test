package cn.com.common.utils.ip;

import cn.com.common.handle.exception.BusinessException;
import cn.com.common.utils.ip.code.IPLocation;
import cn.com.common.utils.ip.code.Location;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * IP处理工具类
 *
 * @author 光明
 * @date 2019-12-17 22:28
 */

public class IpUtils {
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";


    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        System.out.println(request);
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCALHOST.equals(ipAddress)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


    /**
     * 根据ip 获取省份 ,城市
     *
     * @param ip
     * @return
     */
    public Map<String, String> getIpChinaAddr(String ip) {
        final IPLocation ipLocation;
        try {
            ipLocation = new IPLocation("common/src/main/resources/qqwry.dat");
            Location loc = ipLocation.fetchIPLocation(ip);
            Map<String, String> map = new HashMap<>(2);
            map.put("address", loc.country);
            map.put("operator", loc.area);
            return map;
        } catch (Exception e) {
            throw new BusinessException("读取文件数据异常:{}", e);
        }
    }




























}