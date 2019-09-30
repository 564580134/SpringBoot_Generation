package com.mybatis.generation.util.generation;

import com.mybatis.generation.util.jdbc.JDBCUtil;
import com.mybatis.generation.util.velocity.StringUtil;
import com.mybatis.generation.util.velocity.VelocityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mybatis.generation.util.velocity.StringUtil.lineToHump;


/**
 * 代码生成
 *
 * @author Liu Runyong
 * @date 2019/8/15
 */
@Slf4j
public class MybatisGeneratorUtil {

    /**
     * generatorConfig模板路径
     */
    private static String generatorConfig_vm = "/template/GeneratorConfig.vm";
    /**
     * Dao模板路径
     */
    private static String dao_vm = "/template/Dao.vm";
    /**
     * Service模板路径
     */
    private static String service_vm = "/template/Service.vm";
    /**
     * ServiceImpl模板路径
     */
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";
    /**
     * ServiceImpl模板路径
     */
    private static String controller_vm = "/template/Controller.vm";

    /**
     * 根据模板生成generatorConfig.xml文件
     *
     * @param jdbcDriver   驱动路径
     * @param jdbcUrl      链接
     * @param jdbcUsername 帐号
     * @param jdbcPassword 密码
     * @param database     数据库
     * @param tablePrefix  表前缀
     * @param packageName  包名
     * @param author       作者
     */
    public static void generator(
            String jdbcDriver,
            String jdbcUrl,
            String jdbcUsername,
            String jdbcPassword,
            String database,
            String tablePrefix,
            String packageName,
            Map<String, String> lastInsertIdTables,
            String author) throws Exception {
        String os = System.getProperty("os.name");
        String targetProject = "";
        String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "");
        if (os.toLowerCase().startsWith("win")) {
            generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath().replaceFirst("/", "");
            dao_vm = MybatisGeneratorUtil.class.getResource(dao_vm).getPath().replaceFirst("/", "");
            service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
            serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");
            controller_vm = MybatisGeneratorUtil.class.getResource(controller_vm).getPath().replaceFirst("/", "");
            basePath = basePath.replaceFirst("/", "");
        } else {
            generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
            service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();
            dao_vm = MybatisGeneratorUtil.class.getResource(dao_vm).getPath();
            serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();
            controller_vm = MybatisGeneratorUtil.class.getResource(controller_vm).getPath();
        }
        String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";
        targetProject = basePath + targetProject;
        //  mySQL默认使用当前库中的所有表 如果需要自定义 请加上  AND table_name LIKE '" + tablePrefix + "%';"
        String sql = "SELECT table_name,table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "'";
        //oracle
        //String sql = "select table_name from user_tables where tablespace_name ='" + database + "' AND TABLE_NAME LIKE '" + tablePrefix + "_%'";
        log.info("========== 开始生成generatorConfig.xml文件 ==========");
        List<Map<String, Object>> tables = new ArrayList<>();
        try {
            VelocityContext context = new VelocityContext();
            Map<String, Object> table;
            // 查询定制前缀项目的所有表
            JDBCUtil jdbcUtil = new JDBCUtil(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
            // 获取库中所有的表
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            // 循环遍历
            for (Map map : result) {
                System.out.println(map.get("TABLE_NAME"));
                table = new HashMap<>(3);
                table.put("table_name", map.get("TABLE_NAME"));
                table.put("table_annotation", map.get("TABLE_COMMENT"));
                table.put("model_name", lineToHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
                tables.add(table);
            }
            // 释放连接
            jdbcUtil.release();
            // 指定生成文件的位置
            context.put("tables", tables);
            context.put("generator_javaModelGenerator_targetPackage", packageName + ".model");
            context.put("generator_sqlMapGenerator_targetPackage", "mybatis.mapper");
            context.put("generator_javaClientGenerator_targetPackage", packageName + ".dao");
            context.put("targetProject", targetProject);
            context.put("targetProject_sqlMap", basePath);
            context.put("generator_jdbc_password", jdbcPassword);
            context.put("last_insert_id_tables", lastInsertIdTables);
            VelocityUtil.generate(generatorConfig_vm, generatorConfigXml, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> warnings = new ArrayList<>();
        File configFile = new File(generatorConfigXml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(false);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
        velocityCode(tables, basePath, packageName, author);
    }

    /**
     * 生成文件
     *
     * @param tables      表名+注释
     * @param basePath    根路径
     * @param packageName 包名
     * @param author      作者
     */
    public static void velocityCode(List<Map<String, Object>> tables,
                                    String basePath,
                                    String packageName,
                                    String author) throws Exception {
        // 获取daoPath
        String daoPath = basePath + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao";
        // 获取servicePath
        String servicePath = basePath + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/service";
        // 获取serviceImplPath
        String serviceImplPath = basePath + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/service/impl";
        // 获取controllerPath
        String controllerPath = basePath + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/controller";
        // 创建时间
        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        // 循环创建对应的dao、service、serviceImpl、controller
        for (int i = 0; i < tables.size(); i++) {
            // 获取实体Bean名称
            String model = lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
            // 获取数据库表的中文名称
            String tableAnnotation = lineToHump(ObjectUtils.toString(tables.get(i).get("table_annotation")));
            // Dao文件路径+名称
            String dao = daoPath + "/" + model + "Mapper.java";
            // Service文件路径+名称
            String service = servicePath + "/" + model + "Service.java";
            // ServiceImpl文件路径+名称
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
            // Controller文件路径+名称
            String controller = controllerPath + "/" + model + "Controller.java";
            // 创建模板并给定模板需要的备注信息
            VelocityContext context = new VelocityContext();
            context.put("package_name", packageName);
            context.put("model", model);
            context.put("table_annotation", tableAnnotation);
            context.put("entity", StringUtil.toLowerCaseFirstOne(model));
            context.put("createDate", createDate);
            context.put("author", author);
            // 生成dao
            File daoFile = new File(dao);
            if (!daoFile.exists()) {
                VelocityUtil.generate(dao_vm, dao, context);
                log.info(dao);
            }
            // 生成service
            File serviceFile = new File(service);
            if (!serviceFile.exists()) {
                context.put("tableName", model);
                VelocityUtil.generate(service_vm, service, context);
                log.info(service);
            }
            // 生成serviceImpl
            File serviceImplFile = new File(serviceImpl);
            if (!serviceImplFile.exists()) {
                context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
                VelocityUtil.generate(serviceImpl_vm, serviceImpl, context);
                log.info(serviceImpl);
            }
            // 生成controller
            File controllerFile = new File(controller);
            if (!controllerFile.exists()) {
                VelocityUtil.generate(controller_vm, controller, context);
                log.info(controller);
            }
        }
    }

    /**
     * 递归删除非空文件夹
     */
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }

}