package database.make;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import engine.GameObject;
import geometric.Transform;
import geometric.Vector3;
import database.network.NetworkConnection;

public class Create {
    public final static String TABLE_GAMEOBJECTS = "gameobjects";
    public final static List<GameObject> GAMEOBJECTS = new ArrayList<>();

    public static void main(String[] args){
        GameObject g1 = new GameObject("Go1", new Transform(new Vector3(5, 87, 1)));
        GameObject g2 = new GameObject("Go2", new Transform(new Vector3(5, 50, 1), new Vector3(85, 521, 8)));
        GAMEOBJECTS.add(g1);
        GAMEOBJECTS.add(g2);

        try(Connection connection = NetworkConnection.getConnection()){
            Statement stmt = connection.createStatement();
            drop(stmt);
            create(stmt);
            updateGameObjects(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void drop(Statement stmt) throws SQLException{
        System.out.println(TABLE_GAMEOBJECTS + " : DROP");
        String query = "DROP TABLE IF EXISTS " + TABLE_GAMEOBJECTS + " CASCADE;";
        stmt.executeUpdate(query);
    }
    
    public static void create(Statement stmt) throws SQLException{
        System.out.println(TABLE_GAMEOBJECTS + " : CREATE");
        String query = "CREATE TABLE " + TABLE_GAMEOBJECTS + "(gno int PRIMARY KEY, position text, rotation text, scale text);";
        stmt.executeUpdate(query);
    }
    
    public static void updateGameObjects(Statement stmt) throws SQLException{
        for(GameObject g : GAMEOBJECTS){ updateGameObject(stmt, g); }
    }

    public static void updateGameObject(Statement stmt, GameObject g) throws SQLException{
        updateGameObject(stmt, g.getIdentity().getId(), g.getTransform());
    }

    public static void updateGameObject(Statement stmt, int id, Transform transform) throws SQLException{
        updateGameObject(stmt, id, transform.getPosition(), transform.getRotation(), transform.getScale());
    }

    public static void updateGameObject(Statement stmt, int id, Vector3 position, Vector3 rotation, Vector3 scale) throws SQLException{
        System.out.println(TABLE_GAMEOBJECTS + " : UPDATE");
        String query = "INSERT INTO " + TABLE_GAMEOBJECTS + "(gno, position, rotation, scale) VALUES(" + id + ", '" + position + "', '" + rotation + "', '" + scale + "');";
        stmt.executeUpdate(query);
    }
}