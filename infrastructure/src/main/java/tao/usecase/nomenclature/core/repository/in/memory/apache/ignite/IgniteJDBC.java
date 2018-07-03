package tao.usecase.nomenclature.core.repository.in.memory.apache.ignite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Repository
public class IgniteJDBC {

    private final static Logger LOGGER = LoggerFactory.getLogger(IgniteJDBC.class);

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
        Statement sql = conn.createStatement();
        try{
            final InputStream in = this.getClass().getClassLoader().getResourceAsStream(classpathFileName);
            String sqlString = StreamUtils.copyToString(in, Charset.defaultCharset());
            LOGGER.info(String.format("Trying to execute request: %s", sqlString));
            sql.executeUpdate(sqlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String classpathFileName) throws SQLException, ClassNotFoundException {
        executeSqlFromClasspath(classpathFileName);
    }

    public List<Map<String, Object>> getData(String sqlString) throws SQLException {
        Statement sql = conn.createStatement();
        LOGGER.info(String.format("Trying to execute request: %s", sqlString));
        return (List<Map<String, Object>>) sql.executeQuery(sqlString);

    }
}
