/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import chatsystemproject.ServerThread;
import chatsystemproject.User;
import chatsystemproject.ClientThread;
import chatsystemproject.Session;
import chatsystemproject.ListenerThread;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private ClientThread ClThread = null;
    private ServerThread st = null;
    private Scanner scan = new Scanner(System.in);
    private User user2;
    private Session session;
    public SessionWindow() {
        initComponents();
    }
    public SessionWindow(User u,ClientThread clientThread,Session session) {
        //Start the session with the person you want to chat with (user)
        //
        // Session session = new Session(You,user);
        System.out.println("On commence tout");
        initComponents();
        this.session = session;
        this.user2 = u;
        String user = u.getPseudo();
        this.ClThread = clientThread;
        User.setText(user);
        ListenerThread listenerThread = ClThread.getMainSystem(); 
        this.st = listenerThread.getServer(u,this);
        ChatArea.setText("Connexion established .. Session started...");
        System.out.println("Type your message :");

    }
    
    public void addMessage(String Message){
        
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss \t").format(Calendar.getInstance().getTime());
        ChatArea.setText(ChatArea.getText() +"\n" + timeStamp + "  "+User.getText() + " : " + Message);
    }
    
    public javax.swing.JButton getSendButton(){
        return this.SendButton;
    }
    
    public Session getSession(){
        return this.session;
    }
    
    public ServerThread getServerThread(){
        return this.st;
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
        jScrollPane5 = new javax.swing.JScrollPane();
        TextArea = new javax.swing.JTextPane();
        exitButton = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 255));

        ChatArea.setColumns(20);
        ChatArea.setRows(5);
        ChatArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(ChatArea);

        MessageArea.setColumns(20);
        MessageArea.setRows(5);
        MessageArea.setText("Write your message...");
        MessageArea.setAutoscrolls(false);
        jScrollPane3.setViewportView(MessageArea);

        SendButton.setBackground(new java.awt.Color(80, 201, 181));
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

        jScrollPane5.setViewportView(TextArea);

        exitButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        exitButton.setText("X");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(483, 483, 483)
                        .addComponent(User))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(149, 149, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitButton))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(User)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(143, 143, 143))
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
        ChatArea.setText(ChatArea.getText() + "\n" + timeStamp + "<b>You</b> : "+ MessageArea.getText());
        TextArea.setText("<p>"+TextArea.getText() + "\n" + timeStamp + "<b>You</b> : "+ MessageArea.getText()+"</p>");
        st.writeMessage(MessageArea.getText());
        
        
    }//GEN-LAST:event_SendButtonMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        
        if(this.SendButton.isVisible()){//if the send button is still visible, the session is active so we need to send an exit message and close the socket
            String exitMessage = "EXIT|"+this.ClThread.getMainUserPseudo();
            st.writeMessage(exitMessage);
            this.session.endSession(this.st);
            
            try {
                this.st.getSocket().close();
            } catch (IOException ex) {
                Logger.getLogger(SessionWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
 
        }
        else{
            this.session.endSession(this.st);
            this.dispose();
        }
              // TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel1MouseClicked

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
                new SessionWindow(null,null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ChatArea;
    private javax.swing.JTextArea MessageArea;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextPane TextArea;
    private javax.swing.JLabel User;
    private javax.swing.JLabel exitButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
