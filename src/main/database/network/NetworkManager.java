package database.network;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class NetworkManager{
    public final static String USERS = "Users";

    public NetworkManager(){
        start();
    }
    
    public void start(){
        System.out.println("--- Start ---");
        System.out.println();
        try(Connection connection = NetworkConnection.getConnection()){
            System.out.println("Connection reussi !");
            
            Statement stmt = connection.createStatement();
            drop(stmt);
            create(stmt);
            insert(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println("--- End --- ");
    }

    public void drop(Statement stmt) throws SQLException{
        System.out.println(USERS + " : DROP");
        String query = "DROP TABLE IF EXISTS " + USERS + " CASCADE;";
        stmt.executeUpdate(query);
    }
    
    public void create(Statement stmt) throws SQLException{
        System.out.println(USERS + " : CREATE");
        String query = "CREATE TABLE " + USERS + "(id int PRIMARY KEY, pseudo varchar(25));";
        stmt.executeUpdate(query);
    }

    public void insert(Statement stmt) throws SQLException{
        System.out.println(USERS + " : INSERT");
        String query = "INSERT INTO " + USERS + " VALUES(0, 'Synoxy');";
        stmt.executeUpdate(query);
    }
}