package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    public final static String FILE_NAME_CONFIG = "config.prop";
    public final static String PATH_FILE_CONFIG = "res" + File.separator + "network" + File.separator + FILE_NAME_CONFIG;

    private String driver = "";
    private String url = "";
    private String login = "";
    private String password = "";
    
    public DataSource(){
        try {
            loadProperties();
            loadDriver();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadProperties() throws IOException{
        Properties p = new Properties();
        p.load(new FileInputStream(PATH_FILE_CONFIG));
        driver = p.getProperty("driver");
        url = p.getProperty("url");
        login = p.getProperty("login");
        password = p.getProperty("password");
    }
    
    public void loadDriver() throws ClassNotFoundException{
        Class.forName(driver);
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, login, password);
    }
}
