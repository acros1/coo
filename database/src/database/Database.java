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

import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connect connetion = new Connect("C:\\Users\\Alex\\Desktop\\database\\myDataBase.db");
        connetion.connect();
        
        ResultSet resultSet = connetion.query("SELECT * FROM BOOK");
        try {
            while (resultSet.next()) {
                System.out.println("Titre : " + resultSet.getString("Title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        connetion.close();
    }
    
}
