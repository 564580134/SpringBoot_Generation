package com.mybatis.generation.util;

import com.mybatis.generation.util.generation.MybatisGeneratorUtil;
import com.mybatis.generation.util.velocity.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liu Runyong
 * @date 2019/8/15
 * @description
 */
public class GeneratorJavaCode {

    /**
     * 根据命名规范，只修改此常量值即可
     */
    private static String AUTHOR = "Liu Runyong";
    private static String DATABASE = "generation-mybatis";
    private static String TABLE_PREFIX = "tb_";
    private static String PACKAGE_NAME = "com.mybatis.generation";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    /**
     * 需要insert后返回主键的表配置，key:表名,value:主键名
     */
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();

    static {
        LAST_INSERT_ID_TABLES.put("base_user", "userId");
        LAST_INSERT_ID_TABLES.put("active_merchant", "id");
    }

    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER,
                JDBC_URL,
                JDBC_USERNAME,
                JDBC_PASSWORD,
                DATABASE,
                TABLE_PREFIX,
                PACKAGE_NAME,
                LAST_INSERT_ID_TABLES,
                AUTHOR);
    }
}
