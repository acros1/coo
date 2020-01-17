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
        
        ArrayList<String[]> history = myDataBase.getHistory(1, 2);
        String[] message = null;
        System.out.println("----------------History-----------------");

        message = history.get(0);
        System.out.println("User src : " + message[0] + ", User dest : " + message[1] + ", Message : '" + message[2] + "'");
        message = history.get(1);
        System.out.println("User src : " + message[0] + ", User dest : " + message[1] + ", Message : '" + message[2] + "'");
        /*
        for (int i = 0 ; i < history.size() ; i++) {
            message = history.get(i);
            System.out.println("User src : " + message[0] + ", User dest : " + message[1] + ", Message : '" + message[2] + "'");
        }*/
        System.out.println("----------------------------------------");
        
        //myDataBase.addToHistory(1, 2, "le père Noël en tréno");
        
        System.out.println("Alex id is : " + myDataBase.getUserIdByLogin("alex"));
 
        myDataBase.close();
    }
    
}
