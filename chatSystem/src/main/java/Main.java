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
         User user1 = new User(agent1);
         User user2 = new User(agent2);
         
         user1.getAgent().startSession(user2.getAgent().getSystemAddr(),user2.getAgent().getSystemPort());
         
         
     }
     
}
