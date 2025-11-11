package utils;
import java.sql.*;

public class JdbcConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pttk";
    private static final String USER = "root"; // Thay username
    private static final String PASS = "14032004"; // Thay password
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);

        } catch (SQLException se) {
            se.printStackTrace();
            System.err.println("SQLException: " + se.getMessage());
            System.err.println("SQLState: " + se.getSQLState());
            System.err.println("VendorError: " + se.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: " + e.getMessage());
        }
        return connection;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                try {
                    if (!conn.getAutoCommit()) {
                        conn.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Ket noi duoc database roi nay! yeeeee");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ket noi lai database diiii troi oiii!");
        }
    }
}
