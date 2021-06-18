package models;
import java.sql.Connection;
import java.sql.DriverManager;

public class Coneccion {
    private static String server = "jdbc:mysql://localhost:3306/db_productos";
    private static String user      = "root";
    private static String password  = "root";
    public  static Connection conn;

    public static Connection getConection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(server, user, password);
            return conn;
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
}
