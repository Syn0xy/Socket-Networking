package database.account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.network.NetworkConnection;

public class AccountSystem {
    public final static String USERS = "Users";
    private static AccountSystem instance = null;

    private Connection connection;
    private Statement statement;

    public AccountSystem(){
        this.connection = null;
        this.statement = null;
        startConnection();
        start();
    }

    public Connection getConnection(){ return connection; }
    public Statement getStatement(){ return statement; }

    public static AccountSystem getInstance(){
        if(instance == null) instance = new AccountSystem();
        return instance;
    }

    public void closeConnection(){
        try { if(connection != null) connection.close();
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public void start(){
        try {
            drop(statement);
            create(statement);
            insert(statement);
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public void startConnection(){
        try{
            this.connection = NetworkConnection.getConnection();
            this.statement = connection.createStatement();

            System.out.println("Connection reussi !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void drop(Statement stmt) throws SQLException{
        String query = "DROP TABLE IF EXISTS " + USERS + " CASCADE;";
        stmt.executeUpdate(query);
    }
    
    public void create(Statement stmt) throws SQLException{
        String query = "CREATE TABLE " + USERS + "(id SERIAL PRIMARY KEY, email varchar(30), username varchar(25), password varchar(25));";
        stmt.executeUpdate(query);
    }
    
    public void insert(Statement stmt) throws SQLException{
        String query1 = "INSERT INTO " + USERS + "(email, username, password) VALUES('sinoxap@gmail.com', 'Synoxy', '0000');";
        String query2 = "INSERT INTO " + USERS + "(email, username, password) VALUES('testtest@gmail.com', 'Test', 'mdp');";
        stmt.executeUpdate(query1);
        stmt.executeUpdate(query2);
    }

    public static Account login(String username, String password) throws SQLException{
        return getInstance().search(username, password);
    }

    private Account search(String username, String password) throws SQLException{
        Statement stmt = connection.createStatement();
        String query = "SELECT email, username FROM " + USERS + " WHERE username = '" + username + "' AND password = '" + password + "';";
        ResultSet rs = stmt.executeQuery(query);
        String email = null, us = null;
        if(rs.next()) email = rs.getString("email");
        if(rs.next()) us = rs.getString("username");
        return new Account(email, us);
    }
    public static Account register(String email, String username, String password) throws SQLException{
        String query = "INSERT INTO " + USERS + "(email, username, password) VALUES('" + email + "', '" + username + "', '" + password + "');";
        getInstance().getStatement().executeUpdate(query);
        return getInstance().search(username, password);
    }
}
