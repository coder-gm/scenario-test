package cn.com.common.mybatisplus;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisPlus代码生成器
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/7/30 15:50
 */
public class CodeGenerator {


    /**
     * 启动生成代码
     */
    @Test
    public void getCodeGenerator() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //1.全局配置
        mpg.setGlobalConfig(getGlobalConfig());
        //2.数据源配置
        mpg.setDataSource(getDataSourceConfig());
        //3.策略配置(数据库表配置)
        mpg.setStrategy(getStrategyConfig());
        //4.包名策略配置
        mpg.setPackageInfo(getPackageConfig());
        //5. 执行
        mpg.execute();
    }


    /**
     * 全局配置
     *
     * @return
     */
    public GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();

        //是否支持AR模式,如果设置为true,生成出的代码实体类中会继承 Model<T>类
        gc.setActiveRecord(false);
        //作者
        gc.setAuthor("Zhang Guang Ming");
        //生成路径
        //gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setOutputDir("E:\\data\\GeneratorCode");
        //多次生成的情况下是否让文件覆盖
        gc.setFileOverride(true);
        //设置主键生成策略
        gc.setIdType(IdType.ASSIGN_UUID);
        //在xxMapper.xml 中的<ResultMap> 生成基本的映射
        gc.setBaseResultMap(true);
        //在xxMapper.xml 中的生成基本的<sql>片段
        gc.setBaseColumnList(true);
        //是否自动打开生成完成的后的文件夹
        gc.setOpen(true);
        //实体命名方式,默认值：null 例如：%sEntity 生成 UserEntity
        gc.setEntityName(null);
        //mapper 命名方式,默认值：null 例如：%sDao 生成 UserDao
        gc.setMapperName("%sMapper");
        //Mapper xml 命名方式,默认值：null 例如：%sDao 生成 UserDao.xml
        gc.setXmlName("%sMapper");
        //设置生成的Service接口的名字的首字母是否为(I),默认情况下是有I的,
        //例如: IEmployeeService ,去掉I 使用%sService
        gc.setServiceName("%sService");
        //service impl 命名方式, 默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        gc.setServiceImplName("%sServiceImpl");
        //controller 命名方式,默认值：null 例如：%sAction 生成 UserAction
        gc.setControllerName("%sController");

        return gc;
    }


    /**
     * 数据源配置
     *
     * @return
     */
    public DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();

        //数据库类型,该类内置了常用的数据库类型【必须】
        dsc.setDbType(DbType.MYSQL);
        //驱动连接的URL
        dsc.setUrl("jdbc:mysql://47.103.64.174:3306/ffr_remote_follower");
        //驱动名称
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        //数据库连接用户名
        dsc.setUsername("root");
        //数据库连接密码
        dsc.setPassword("Gm859230");

        return dsc;
    }


    /**
     * 策略配置(数据库表配置)
     *
     * @return
     */
    public StrategyConfig getStrategyConfig() {
        StrategyConfig sc = new StrategyConfig();

        //开启大写命名
        sc.setCapitalMode(true);
        //数据库下滑线转驼峰命名
        sc.setNaming(NamingStrategy.underline_to_camel);
        //表前缀
        sc.setTablePrefix("comm_");
        //需要包含的表名，当enableSqlFilter为false时，允许正则表达式（与exclude二选一配置）
        //需要生成代码的表名
        sc.setInclude("check_process", "steps_check","device_operation_video");
        //是否生成实体时，生成字段注解
        sc.setEntityTableFieldAnnotationEnable(true);
        //乐观锁属性名称
        sc.setVersionFieldName("version");
        //逻辑删除属性名称
        sc.setLogicDeleteFieldName("is_delete");
        // 表的自动填充字段
        List<TableFill> tableFills = new ArrayList<>();
        tableFills.add(new TableFill("create_user", FieldFill.INSERT));
        tableFills.add(new TableFill("create_Time", FieldFill.INSERT));
        tableFills.add(new TableFill("update_user", FieldFill.UPDATE));
        tableFills.add(new TableFill("update_time", FieldFill.UPDATE));
        sc.setTableFillList(tableFills);
        //是否使用lombok
        sc.setEntityLombokModel(true);
        //是否生成@RestController 控制器
        sc.setRestControllerStyle(true);

        return sc;
    }

    /**
     * 包名策略配置
     *
     * @return
     */
    public PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        //parent  父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent("com.abbott.ffr");
        //Entity包名
        pc.setEntity("model.po");
        //Service包名
        pc.setService("service");
        //Service Impl包名
        pc.setServiceImpl("service.impl");
        //Mapper包名
        pc.setMapper("mapper");
        //Mapper XML包名
        pc.setXml("xml");
        //Controller包名
        pc.setController("controller");

        return pc;
    }


}