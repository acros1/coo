/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
import java.util.ArrayList;

public class Connect {
    private static String RelativePath = "..\\..\\chatSystem.db";
    private static String pathToAdd = "\\ChatSystemProject2\\chatSystem.db";
    
    private String DBPath = null;
    private Connection connection = null;
    private Statement statement = null;
 
    public Connect() {
        File chatSystemDB = new File(RelativePath);
        try {
            DBPath = chatSystemDB.getParentFile().getCanonicalPath() + pathToAdd;
            System.out.println(DBPath);
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
        ResultSet resultSet = query("SELECT * FROM Users WHERE login = '" + login + 
                                    "' AND password = '" + password + "';");
        try {
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ArrayList<ArrayList<String>> getHistory(int idUserSrc, int idUserDest) {
        
        ArrayList<ArrayList<String>> history = new ArrayList<ArrayList<String>>();
        
        ResultSet resultSet = query("SELECT * FROM Messages WHERE idUserSrc = " + idUserSrc + 
                                    " AND idUserDest = " + idUserDest + 
                                    " OR idUserSrc = " + idUserDest + 
                                    " AND idUserDest = " + idUserSrc + ";");
        
        try {
            while (resultSet.next()) {
                ArrayList<String> message = new ArrayList<String>();
                message.add(resultSet.getString("idUserSrc"));
                message.add(resultSet.getString("idUserDest"));
                message.add(resultSet.getString("message"));
                message.add(resultSet.getString("datetime"));
                history.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return history;
    }
    
    public void addToHistory(int idUserSrc, int idUserDest, String message, String datetime) {
        //String query = "INSERT INTO Messages (idUserSrc, idUserDest, message) VALUES (" + idUserSrc + ", " + idUserDest + ", \"" + message + "\");";
        String query = "INSERT INTO Messages (idUserSrc, idUserDest, message, datetime) VALUES (?, ?, ?, ?);";
        try {
           PreparedStatement pStatement = connection.prepareStatement(query);
           pStatement.setInt(1, idUserSrc);
           pStatement.setInt(2, idUserDest);
           pStatement.setString(3, message);
           pStatement.setString(4, datetime);
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
