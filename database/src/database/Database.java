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
        
        myDataBase.addToHistory(1, 2, "le père Noël en tréno");

        ArrayList<ArrayList<String>> history = myDataBase.getHistory(1, 2);
        System.out.println("----------------History-----------------");
        for (int iHistory = 0 ; iHistory < history.size() ; iHistory++) {
            String idUserSrc = history.get(iHistory).get(0);
            String idUserDest = history.get(iHistory).get(1);
            String message = history.get(iHistory).get(2);
            System.out.println("User src : " + idUserSrc + ", User dest : " + idUserDest + ", Message : '" + message + "'");
        }
        System.out.println("----------------------------------------");
        
        
        System.out.println("Alex id is : " + myDataBase.getUserIdByLogin("alex"));
 
        myDataBase.close();
    }
    
}
