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
         Agent.createUser("Maelnedlc","abcd");
         Agent.createUser("nedellec","1234");
         Agent.createUser("Mael325478","password");
         Agent.createUser("Mael1","motdepasse");
         
         Agent.connection("nedellec","test");
         Agent agent2 = new Agent();
         User user1 = new User(agent1);
         User user2 = new User(agent2);
         Message msg1 = new Message(user1,user2,"premier test usr1 -> usr2");
         Message msg2 = new Message(user2,user1,"premier test usr2 -> usr1");
         Message msg3 = new Message(user1,user2,"deuxième test usr1 -> usr2");
         agent1.sendMessage(msg1);
         agent2.sendMessage(msg2);
         agent1.sendMessage(msg3);
         
        // agent1.getHistory(user1,user2);
         
     }
     
}
