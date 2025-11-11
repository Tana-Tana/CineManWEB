package dao;

import utils.JdbcConnection;

import java.sql.Connection;

public class DAO {
    private Connection con = null;

    public DAO() {
        con = JdbcConnection.getConnection();
    }

    public Connection getConnection() {
        return con;
    }
}
