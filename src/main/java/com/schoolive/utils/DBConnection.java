package com.schoolive.utils;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/schoolive?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
