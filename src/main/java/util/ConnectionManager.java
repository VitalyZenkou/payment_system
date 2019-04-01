package util;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

    private static final String URL = "jdbc:postgresql:payment_system";
    //            PropertiesUtil.get("db.url");
    private static final String USER = "postgres";
    //        PropertiesUtil.get("db.username");
    private static final String PASSWORD = "root";
//        PropertiesUtil.get("db.password");

    @SneakyThrows
    public static Connection get() {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
