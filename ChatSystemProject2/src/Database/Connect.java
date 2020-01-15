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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connect {
    private static String RelativePath = "..\\..\\ChatSystem.db";
    private static String pathToAdd = "\\coo\\ChatSystemProject\\myDataBase.db";
    
    private String DBPath = null;
    private Connection connection = null;
    private Statement statement = null;
 
    public Connect(String dBPath) {
        DBPath = dBPath;
        
        File chatSystemDB = new File(RelativePath);
        try {
            DBPath = chatSystemDB.getParentFile().getCanonicalPath() + pathToAdd;
        } catch (IOException e) {
            e.printStackTrace();
        }
        connect();
    }
 
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            statement = connection.createStatement();
            System.out.println("Connection to " + DBPath);
        } catch (ClassNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            System.out.println("Connection error");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("Connection error");
        }
    }
 
    public void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet query(String request) {
       ResultSet resultat = null;
       try {
           resultat = statement.executeQuery(request);
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Resquest error : " + request);
       }
       return resultat;
 
   }
}
