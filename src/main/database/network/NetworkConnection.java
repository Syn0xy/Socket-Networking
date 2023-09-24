package database.network;

import java.sql.Connection;
import java.sql.SQLException;

import database.DataSource;

public class NetworkConnection {
    public static Connection getConnection() throws SQLException{
        DataSource ds = new DataSource();
        return ds.getConnection();
    }
}