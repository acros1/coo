/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author al_cros
 */
import java.util.*;

public class Message {
    Date date;
    User usr1;
    User usr2;
    String content;
    
    
    public List<User> getUsers(){
        List<User> userList = new ArrayList<User>();
        userList.add(usr1);
        userList.add(usr2);
        return userList;
    }
    
    public String getContent(){
        return this.content;
    }
    
}
