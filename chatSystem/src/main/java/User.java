/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author al_cros
 */
public class User {
    
    public String pseudo;
    public String login;
    public String passwd;
    public int Id;
    public Agent agent;
    
    public User(Agent agent) {
        this.agent = agent;  
    }
    
    public int changePseudo(String newPseudo) {
        int ret = -1;
        if(agent.isPseudoValid(newPseudo)){
            this.pseudo = newPseudo;
            ret = 0;
        }
        
        return ret;
    }
    
    public int choosePseudo(String newPseudo) {
        return 0;
    }
    
    public Agent getAgent(){
        return this.agent;
    }
    
    public int getId(){
        return this.Id;
    }
    
}
