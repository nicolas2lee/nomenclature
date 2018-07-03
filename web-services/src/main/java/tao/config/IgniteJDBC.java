package tao.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class IgniteJDBC {

    public void main() throws ClassNotFoundException, SQLException {
            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

            Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
            createDatabaseTables(conn);
            insertData(conn);
            getData(conn);
    }

    private void createDatabaseTables(Connection conn) throws SQLException {
        Statement sql = conn.createStatement();
        sql.executeUpdate(" CREATE TABLE IF NOT EXISTS T_PAYS (" +
                " CODE VARCHAR(3) PRIMARY KEY, NOM_PAYS_COURT VARCHAR(30), CODE_PAYS_2 VARCHAR(2), " +
                " CODE_PAYS_3 VARCHAR(3), PAYS_NUMERO VARCHAR(3), PAYS_LANGUE VARCHAR(20), " +
                " LANG VARCHAR(2), PAYS_CONTINENT_ID VARCHAR(2), STATUS VARCHAR(1) ) " +
                " WITH \"template=replicated\"");

        System.out.println("table created");
    }

    private void insertData(Connection conn) throws SQLException {

        PreparedStatement sql =
                conn.prepareStatement("INSERT INTO T_PAYS (CODE, NOM_PAYS_COURT, CODE_PAYS_2, CODE_PAYS_3, PAYS_NUMERO, PAYS_LANGUE, LANG, PAYS_CONTINENT_ID, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        sql.setString(1, "123");
        sql.setString(2, "France");
        sql.setString(3, "FR");
        sql.setString(4, "FRA");
        sql.setString(5, "1");
        sql.setString(6, "'Français'");
        sql.setString(7, "FR");
        sql.setString(8, "2");
        sql.setString(9, "1");
        sql.executeUpdate();

    }

    private void getData(Connection conn) throws SQLException {

        Statement sql = conn.createStatement();
        ResultSet rs = sql.executeQuery("SELECT * " +
                " FROM t_pays ") ;

        while (rs.next())
            System.out.println(rs.getString(1) + ", " + rs.getString(2));
    }
}
