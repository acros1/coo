/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
/**
 *
 * @author al_cros
 */
public class Main {
     public static void main(String arg[]) throws IOException  {
        Agent agent1 = new Agent();
        Agent agent2 = new Agent();
        User user1 = User.userLogin();
        User user2 = User.userLogin();
        Session ses1 = new Session(user1,user2);
        user1.choosePseudo();
        user2.choosePseudo();
        ses1.getHistory();
         
     }
     
}
