package org.leomo.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.leomo.chapter2.util.CollectionUtil;
import org.leomo.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by LeeToSun on 2017/5/26
 */
public class DatabaseHelper {

    private DatabaseHelper() {

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    //-QueryRunner- 对象可以面向实体对象进行查询
    private static final QueryRunner QUERY_RUNNER;

    //连接池
    private static final BasicDataSource DATA_SOURCE;

    /*
     * -ThreadLocal- 隔离线程的容器
     * 为了确保一个线程只有一个Connection，使用ThreadLocal存放本地线程变量
     * 在这里的Connection一定不会出现线程不安全问题
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    static {
        QUERY_RUNNER = new QueryRunner();
        DATA_SOURCE = new BasicDataSource();
        CONNECTION_HOLDER = new ThreadLocal<Connection>();

        Properties props = PropsUtil.loadProps("config.properties");
        String driver = props.getProperty("jdbc.driver");
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 获取数据库连接
     */
    private static Connection getConnection() {
        //每次获取Connection时，首先在ThreadLocal中寻找
        Connection conn = CONNECTION_HOLDER.get();
        //如果不存在
        if (conn == null) {
            try {
                //创建一个新的Connection
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                //将Connection放入ThreadLocal中
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 查询实体集合
     * DbUtils首先执行SQL语句并返回一个ResultSet，然后通过反射去创建并初始化实体对象
     * 由于我们需要返回一个List，所以这里使用BeanListHandler
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * 查询实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            entity = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    public static int executeUpdate(String sql, Object... params) {
        int rows;
        try {
            rows = QUERY_RUNNER.update(getConnection(), sql, params);
        } catch (SQLException e) {
            LOGGER.error("update failure", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> filedMap) {
        if (CollectionUtil.isEmpty(filedMap)) {
            LOGGER.error("can not insert entity:filedMap is empty");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        //出过错误，SQL没有拼对
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String filedName : filedMap.keySet()) {
            columns.append(filedName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + "VALUES" + values;
        Object[] params = filedMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityCLass, Map<String, Object> filedMap, long id) {
        if (CollectionUtil.isEmpty(filedMap)) {
            LOGGER.error("can not update entity:filedMap is empty");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityCLass) + " SET ";
        StringBuilder columns = new StringBuilder("");
        for (String filedName : filedMap.keySet()) {
            columns.append(filedName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";
        List<Object> paramList = new ArrayList<Object>();
        //过程中出现NotSerializableException错误，原因是之前在此处用了add，实际上应该使用addAll
        paramList.addAll(filedMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 获取实体类名
     */
    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName().toLowerCase();
    }

    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = reader.readLine()) != null) {
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

}
