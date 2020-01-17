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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connect {
    private static String RelativePath = "..\\..\\chatSystem.db";
    private static String pathToAdd = "\\coo\\ChatSystemProject2\\chatSystem.db";
    
    private String DBPath = null;
    private Connection connection = null;
    private Statement statement = null;
 
    public Connect() {
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
    
    public void printLogin() {
        ResultSet resultSet = query("SELECT * FROM Users");
        try {
            while (resultSet.next()) {
                System.out.println("Login : " + resultSet.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isLogCorrect(String login, String password) {
        ResultSet resultSet = query("SELECT * FROM Users WHERE login = '" + login + "' AND password = '" + password + "';");
        try {
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ResultSet getHistory(int idUserSrc, int idUserDest) {
        ResultSet history = query("SELECT * FROM Messages WHERE idUserSrc = " + idUserSrc + " AND idUserDest = " + idUserDest + " OR idUserSrc = " + idUserDest + " AND idUserDest = " + idUserSrc + ";");
        
        return history;
    }
    
    public void addToHistory(int idUserSrc, int idUserDest, String message) {
        //String query = "INSERT INTO Messages (idUserSrc, idUserDest, message) VALUES (" + idUserSrc + ", " + idUserDest + ", \"" + message + "\");";
        String query = "INSERT INTO Messages (idUserSrc, idUserDest, message) VALUES (?, ?, ?);";
        try {
           PreparedStatement pStatement = connection.prepareStatement(query);
           pStatement.setInt(1, idUserSrc);
           pStatement.setInt(2, idUserDest);
           pStatement.setString(3, message);
           pStatement.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Insert message error : " + query);
       }
    }
    
    public int getUserIdByLogin(String login) {
        int userId = -1;
        
        ResultSet resultSet = query("SELECT id FROM Users WHERE login = '" + login + "';");
        try {
            while (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userId;
    }
    
}