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
import java.util.ArrayList;

public class Database {
    
    private static String RelativePath = "..\\..\\myDataBase.db";
    private static String pathToAdd = "\\coo\\database\\myDataBase.db";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connect myDataBase = new Connect();
        
        myDataBase.printLogin();
        
        System.out.println(myDataBase.isLogCorrect("alex", "alex"));
        System.out.println(myDataBase.isLogCorrect("alex", "mael"));
        
        ResultSet history = myDataBase.getHistory(1, 2);
        
        try {
            while (history.next()) {
                System.out.println("Message : " + history.getString("message"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        myDataBase.addToHistory(1, 2, "mes couilles en tréno");
 
        myDataBase.close();
    }
    
}
