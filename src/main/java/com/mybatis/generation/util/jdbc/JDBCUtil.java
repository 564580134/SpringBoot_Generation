package com.mybatis.generation.util.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据库连接
 *
 * @author Liu Runyong
 * @date 2019/8/15
 */
public class JDBCUtil {

    /**
     * 定义数据库的链接
     */
    private Connection conn;
    /**
     * 定义sql语句的执行对象
     */
    private PreparedStatement pst;
    /**
     * 定义查询返回的结果集合
     */
    private ResultSet rs;

    /**
     * 初始化
     *
     * @param driver   连接
     * @param url      地址
     * @param username 用户名
     * @param password 密码
     */
    public JDBCUtil(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新数据
     *
     * @param sql    语句
     * @param params 参数
     */
    public boolean updateByParams(String sql, List params) throws SQLException {
        // 影响行数
        int result = -1;
        pst = conn.prepareStatement(sql);
        int index = 1;
        // 填充sql语句中的占位符
        if (null != params && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(index++, params.get(i));
            }
        }
        result = pst.executeUpdate();
        return result > 0 ? true : false;
    }

    /**
     * 查询多条记录
     *
     * @param sql    语句
     * @param params 参数
     */
    public List<Map> selectByParams(String sql, List params) throws SQLException {
        List<Map> list = new ArrayList<>();
        int index = 1;
        pst = conn.prepareStatement(sql);
        if (null != params && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(index++, params.get(i));
            }
        }
        rs = pst.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int colsLen = metaData.getColumnCount();
        while (rs.next()) {
            Map map = new HashMap(colsLen);
            for (int i = 0; i < colsLen; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object columnValue = rs.getObject(columnName);
                if (null == columnValue) {
                    columnValue = "";
                }
                map.put(columnName, columnValue);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 释放连接
     */
    public void release() {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != pst) {
                pst.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("释放数据库连接");
    }

}
