package cn.com.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @version: V1.0
 * @author: zgming
 * @className: ModuleNameEnum
 * @packageName: cn.com.common.model.enums
 * @description: 模块名称枚举
 * @data: 2020/9/6 22:02
 **/
@Slf4j
@AllArgsConstructor
public enum ModuleNameEnum {
    API_GW("8081", "api-gw", "API 网关"),
    AUTH_SERVICE("8082", "auth-service", "微服务权限认证服务"),
    SIZE_QUERY("8083", "size-query", "尺寸查"),
    QUOTATION_SYSTEM("8084", "quotation-system", "报价管理系统"),
    FILE_MANAGE("8085", "file-mange", "文件管理"),
    LOGS_MANAGE("8086", "logs-manage", "日志管理"),
    SECURITY_MANAGE("8087", "security-manage", "权限管理模块"),
    TEST_CENTER("8088", "test-center", "测试中心模块"),
    DATA_DOCKING_PLATFORM("8089", "smart-office", "智慧办公");
    /**
     * 端口号
     **/
    @Getter
    private String port;

    /**
     * 模块名称
     **/
    @Getter
    private String moduleName;

    /**
     * 模块名称
     **/
    @Getter
    private String moduleNameCN;


    /**
     * 根据端口号获取模块名称
     *
     * @Author: zgming
     * @Param: port
     * @Return java.lang.String
     * @Throws:
     **/
    public static String getModuleName(String url) {
        for (ModuleNameEnum moduleNameEnum : ModuleNameEnum.values()) {
            if (url.contains(moduleNameEnum.port)) {
                return moduleNameEnum.getModuleName();
            }
        }
        return "";
    }


    /**
     * 根据端口号获取模块中文名称
     *
     * @Author: zgming
     * @Param: port
     * @Return java.lang.String
     * @Throws:
     **/
    public static String getModuleNameCn(String url) {
        for (ModuleNameEnum moduleNameEnum : ModuleNameEnum.values()) {
            if (url.contains(moduleNameEnum.port)) {
                return moduleNameEnum.getModuleNameCN();
            }
        }
        return "";
    }


}