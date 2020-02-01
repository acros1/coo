/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import chatsystemproject.ServerThread;
import chatsystemproject.User;
import chatsystemproject.ClientThread;
//import chatsystemproject.Session;
import chatsystemproject.ListenerThread;
import database.Connect;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 *
 * @author Maeln
 */
public class SessionWindow extends javax.swing.JFrame {

    /**
     * Creates new form SessionWindow
     */
    private ClientThread clientThread = null;
    private ServerThread st = null;
    private User user2;
    
    private Connect chatSystemDB = null;
    
    public SessionWindow() {
        initComponents();
    }
    
    public SessionWindow(User u, ClientThread clientThread, Connect chatSystemDB) {

        initComponents();
        this.user2 = u;
        String user = u.getPseudo();
        this.clientThread = clientThread;
        User.setText(user);
        this.st = this.clientThread.getMainSystem().getServer(u,this);
        this.st.setsessionWindow(this);
        this.chatSystemDB = chatSystemDB;
        System.out.println("Type your message :");
        this.getHistory();


    }
    public SessionWindow(User u, ClientThread clientThread, Connect chatSystemDB,ServerThread serverThread) {

        initComponents();
        this.user2 = u;
        String user = u.getPseudo();
        this.clientThread = clientThread;
        User.setText(user);
        this.st = serverThread;
        this.chatSystemDB = chatSystemDB;
        System.out.println("Type your message :");
        this.getHistory();
        this.setLocationRelativeTo(null);


    }
    
    public void addMessage(String message){
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ").format(Calendar.getInstance().getTime());
        ChatArea.setText(ChatArea.getText() + "\n" + timeStamp + " "+ User.getText() + " : " + message);
        ChatAr.setText("<html>"+ChatAr.getText() + "\n" + timeStamp + " "+ "<b>" +User.getText() + "</b> : " + message+"</html>");
                // Adding the message to history in DB
                System.out.println(clientThread);
                System.out.println(chatSystemDB);
        int idMainUser = chatSystemDB.getUserIdByLogin(clientThread.getLogin());
        int idUser2 = chatSystemDB.getUserIdByLogin(user2.getLogin());
        chatSystemDB.addToHistory(idMainUser, idUser2, message, timeStamp);
    }
    
    public void getHistory() {
        int idMainUser = chatSystemDB.getUserIdByLogin(clientThread.getLogin());
        int idUser2 = chatSystemDB.getUserIdByLogin(user2.getLogin());
        ArrayList<ArrayList<String>> history = chatSystemDB.getHistory(idMainUser, idUser2);

        for (int iHistory = 0 ; iHistory < history.size() ; iHistory++) {
            System.out.println("getting history");
            int idsrc = Integer.parseInt(history.get(iHistory).get(0));
            String message = history.get(iHistory).get(2);
            String datetime = history.get(iHistory).get(3);
            
            String user = null;
            if(idsrc == idMainUser){
                user = this.clientThread.getMainUserPseudo();
                ChatArea.setText(ChatArea.getText() + "\n" + datetime + "  " + user + " : " + message);
            }
            else{
                user = User.getText();
                ChatArea.setText(ChatArea.getText() + "\n" + datetime + "  " + user + " : " + message);
            }
            
            
        }
        ChatArea.setText(ChatArea.getText() + "\nConnexion established .. Session started...");
    }
    
    public javax.swing.JButton getSendButton(){
        return this.SendButton;
    }
    /*
    public Session getSession(){
        return this.session;
    }*/
    
    public ServerThread getServerThread(){
        return this.st;
    }
    
    public void setServerThread(ServerThread st){
        this.st = st;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        ChatArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        MessageArea = new javax.swing.JTextArea();
        SendButton = new javax.swing.JButton();
        User = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatAr = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 255));

        ChatArea.setColumns(20);
        ChatArea.setRows(5);
        ChatArea.setToolTipText("");
        ChatArea.setWrapStyleWord(true);
        ChatArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(ChatArea);

        MessageArea.setColumns(20);
        MessageArea.setRows(5);
        MessageArea.setText("Write your message...");
        MessageArea.setAutoscrolls(false);
        MessageArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MessageAreaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(MessageArea);

        SendButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        SendButton.setText("Send");
        SendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SendButtonMouseClicked(evt);
            }
        });
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(ChatAr);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(User, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(149, 149, 149))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SendButtonActionPerformed

    private void SendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendButtonMouseClicked
        // TODO add your handling code here:
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss ").format(Calendar.getInstance().getTime());        
        //MESSAGE YOU'VE SEND IS DISPLAYED
        ChatArea.setText(ChatArea.getText() + "\n" + timeStamp + "You : "+ MessageArea.getText());
        ChatAr.setText("<html>"+ChatAr.getText() + "\n" + timeStamp + "<b>You</b> : "+ MessageArea.getText()+"</html>");
        //Calling the method in the server to send the message
        st.writeMessage(MessageArea.getText());
     
    }//GEN-LAST:event_SendButtonMouseClicked

    private void MessageAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessageAreaMouseClicked
        if(MessageArea.getText().equals("Write your message...")){
           MessageArea.setText(""); 
        }
        
    }//GEN-LAST:event_MessageAreaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SessionWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SessionWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SessionWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SessionWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new SessionWindow(null,null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane ChatAr;
    private javax.swing.JTextArea ChatArea;
    private javax.swing.JTextArea MessageArea;
    private javax.swing.JButton SendButton;
    private javax.swing.JLabel User;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
