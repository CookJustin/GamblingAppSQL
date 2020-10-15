/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author monke
 */
public class DBConnection {
     
    //JDBC URL
    private static final String jdbcURL = "jdbc:mysql://sql3.freesqldatabase.com/sql3370850";
   
    //Driver Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    public static Connection conn = null;
    
    private static final String username = "sql3370850"; //Username
    private static final String password = "Rk7qJx8wtl"; //Password
    
    public static Connection startConnection(){
       
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
           
            System.out.println("Connection Successful");
        } catch (ClassNotFoundException ex) { 
            System.out.print("Catched");
              System.out.println(ex.getMessage());
        } catch (SQLException ex) {
             System.out.print("Catched");
           System.out.println(ex.getMessage());
        }
        return conn;
    }
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection Closed");
        } catch (SQLException ex) {
            System.out.println("closeConnection(); Error");
        }
        
    }
            
            
}
