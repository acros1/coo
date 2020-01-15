/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Alex
 */

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    
    private static String RelativePath = "..\\..\\myDataBase.db";
    private static String pathToAdd = "\\coo\\database\\myDataBase.db";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File myDataBase = new File(RelativePath);
        Connect connetion = null;
        try {
            connetion = new Connect(myDataBase.getParentFile().getCanonicalPath() + pathToAdd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connetion.connect();
        
        ResultSet resultSet = connetion.query("SELECT * FROM Users");
        try {
            while (resultSet.next()) {
                System.out.println("Login : " + resultSet.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        connetion.close();
    }
    
}
