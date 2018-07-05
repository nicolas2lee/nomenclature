package com.bnpparibas.dsibddf.nomenclature.infrastructure.core.repository.in.memory.apache.ignite;

import com.bnpparibas.dsibddf.nomenclature.infrastructure.resource.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("inmemory")
@Repository
@Slf4j
public class IgniteJDBC {

    @Value("${ignite.jdbc.driver.class.name}")
    private String igniteJdbcThinDriverClassName;

    @Value("${ignite.url}")
    private String igniteUrl;

    private static Connection conn;

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        try {
            LOGGER.info(String.format("ignite.jdbc.driver.class.name property found in config file is: %s",igniteJdbcThinDriverClassName ));
            Class.forName(this.igniteJdbcThinDriverClassName);
        } catch (ClassNotFoundException e) {
            LOGGER.error(String.format("Could not find class with classname: %s", igniteJdbcThinDriverClassName), e);
            LOGGER.info("Try to set default IgniteJdbcThinDriver with org.apache.ignite.IgniteJdbcThinDriver");
            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
        }
        LOGGER.info(String.format("ignite url is %s", igniteUrl ));
        return DriverManager.getConnection(igniteUrl);
    }

    public void createDatabaseTables(String classpathFileName) throws SQLException, ClassNotFoundException {
        executeSqlFromClasspath(classpathFileName);
    }

    private void executeSqlFromClasspath(String classpathFileName) throws SQLException, ClassNotFoundException {
        if (conn == null) conn = createConnection();
        val sql = conn.createStatement();
        try{
            final InputStream in = this.getClass().getClassLoader().getResourceAsStream(classpathFileName);
            val sqlString = StreamUtils.copyToString(in, Charset.defaultCharset());
            LOGGER.info(String.format("Trying to execute request: %s", sqlString));
            sql.executeUpdate(sqlString);
        } catch (IOException e) {
            throw new ResourceNotFoundException("Err.00001", String.format("Could not find sql script from class path: %s", classpathFileName));
        }
    }

    public void insertData(String classpathFileName) throws SQLException, ClassNotFoundException {
        executeSqlFromClasspath(classpathFileName);
    }

    List<Map<String, Object>> getMultiRowData(String sqlString) throws SQLException {
        val sql = conn.createStatement();
        LOGGER.info(String.format("Trying to execute request: %s", sqlString));
        val resultSet = sql.executeQuery(sqlString);
        return convertResultSetToListMap(resultSet);
    }

    int getCount(String sqlString) throws SQLException {
        val sql = conn.createStatement();
        LOGGER.info(String.format("Trying to execute request: %s", sqlString));
        val resultSet = sql.executeQuery(sqlString);
        if (resultSet.next())
            return resultSet.getInt(1);
        throw new SQLException(String.format("Failed to run sql %s", sqlString));
    }

    private List<Map<String, Object>> convertResultSetToListMap(ResultSet resultSet) throws SQLException {
        val metaData = resultSet.getMetaData();
        List<Map<String, Object>> result = new ArrayList<>();
        val columns = metaData.getColumnCount();
        while(resultSet.next()){
            Map<String, Object> map = new HashMap<>(columns);
            for (int i=1; i<=columns; i++){
                map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            result.add(map);
        }
        return result;
    }

    Map<String, Object> getSingleRowData(String sqlString) throws SQLException {
        val sql = conn.createStatement();
        LOGGER.info(String.format("Trying to execute request: %s", sqlString));
        val resultSet = sql.executeQuery(sqlString);
        return convertResultSetTotMap(resultSet);
    }

    private Map<String, Object> convertResultSetTotMap(ResultSet resultSet) throws SQLException {
        val metaData = resultSet.getMetaData();
        val columns = metaData.getColumnCount();
        Map<String, Object> map = new HashMap<>(columns);
        if (resultSet.next()){
            for (int i=1; i<=columns; i++){
                map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
        }
        return map;
    }
}
