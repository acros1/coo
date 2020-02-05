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
import Database.Connect;
import java.awt.Color;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.lang.Object.*;

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
    private int mouseX;
    private int mouseY;
    private Connect chatSystemDB = null;
    
    public SessionWindow() {
        initComponents();
    }
    
    /* 
    there are two constructors, because there are two case when an instance SessionWindow must be created :
        1- When the user initialize a session by double clicking on a user in the application window
        2- When the user receive a message from another user
    In the first case the session window has to create the server while on the second the server is created when the first message is received in "Listener thread"
    */
    public SessionWindow(User u, ClientThread clientThread, Connect chatSystemDB) {
        
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1,1,1, Color.BLACK));
        initComponents();
        this.ChatArea.setEditable(false);
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
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1,1,1, Color.BLACK));
        initComponents();
        this.ChatArea.setEditable(false);
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
                // Adding the message to history in DB
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
    
    public void sendMessage() throws IOException{
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss ").format(Calendar.getInstance().getTime());        
        //MESSAGE YOU'VE SEND IS DISPLAYED
        ChatArea.setText(ChatArea.getText() + "\n" + timeStamp + "You : "+ MessageArea.getText());

        //Calling the method in the server to send the message
        st.writeMessage(MessageArea.getText());
        //adding to the database
        int idMainUser = chatSystemDB.getUserIdByLogin(clientThread.getLogin());
        int idUser2 = chatSystemDB.getUserIdByLogin(user2.getLogin());
        chatSystemDB.addToHistory(idMainUser, idUser2, MessageArea.getText(), timeStamp);
        MessageArea.setText("Write your message...");
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
           return fileName.substring(fileName.lastIndexOf(".")+1); 
        }
        else {
            return "";
        }
    }
    
    // -------- SETTER AND GETTER -------------
    public javax.swing.JButton getSendButton(){
        return this.SendButton;
    }
    
    public ServerThread getServerThread(){
        return this.st;
    }
    
    public void setServerThread(ServerThread st){
        this.st = st;
    }
    
    public String getUser(){
        return this.user2.getPseudo();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        ChatArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        MessageArea = new javax.swing.JTextArea();
        SendButton = new javax.swing.JButton();
        User = new javax.swing.JLabel();
        SendFilesButton = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        exitButton = new javax.swing.JLabel();
        reduceButton = new javax.swing.JLabel();
        BorderImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(698, 323));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        ChatArea.setColumns(20);
        ChatArea.setRows(5);
        ChatArea.setToolTipText("");
        ChatArea.setWrapStyleWord(true);
        ChatArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        MessageArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MessageAreaKeyPressed(evt);
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

        SendFilesButton.setText("send files");
        SendFilesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SendFilesButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SendFilesButtonMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(SendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(SendFilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(SendFilesButton)))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel2.setLayout(null);

        exitButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        exitButton.setText("X");
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButtonMouseEntered(evt);
            }
        });
        jPanel2.add(exitButton);
        exitButton.setBounds(670, 0, 16, 29);

        reduceButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        reduceButton.setText("-");
        reduceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reduceButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reduceButtonMouseEntered(evt);
            }
        });
        jPanel2.add(reduceButton);
        reduceButton.setBounds(650, 0, 10, 29);

        BorderImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Borderimg.jpg"))); // NOI18N
        BorderImg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                BorderImgMouseDragged(evt);
            }
        });
        BorderImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BorderImgMousePressed(evt);
            }
        });
        jPanel2.add(BorderImg);
        BorderImg.setBounds(0, -3, 700, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // -------- Mouse and Key events -------------
    
    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SendButtonActionPerformed

    private void SendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendButtonMouseClicked
        // TODO add your handling code here:
        if(!MessageArea.getText().equals("")){
            try {
                sendMessage();
            } catch (IOException ex) {
                Logger.getLogger(SessionWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SendButtonMouseClicked

    private void MessageAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessageAreaMouseClicked
        if(MessageArea.getText().equals("Write your message...")){
           MessageArea.setText(""); 
        }
    }//GEN-LAST:event_MessageAreaMouseClicked

    private void reduceButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reduceButtonMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_reduceButtonMouseClicked

    private void reduceButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reduceButtonMouseEntered
        reduceButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_reduceButtonMouseEntered

    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_exitButtonMouseClicked

    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_exitButtonMouseEntered

    private void MessageAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MessageAreaKeyPressed
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            if(!MessageArea.getText().equals("")){
                try {
                    sendMessage();
                } catch (IOException ex) {
                    Logger.getLogger(SessionWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_MessageAreaKeyPressed

    private void SendFilesButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendFilesButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_SendFilesButtonMouseEntered

    private void SendFilesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendFilesButtonMouseClicked
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f= chooser.getSelectedFile();
        String extension = getFileExtension(f);
        if(extension.equals("pdf")){
            try {
                this.st.sendPdfFile(f);
            } catch (IOException ex) {
                Logger.getLogger(SessionWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(extension.equals("png")){
            try {
                this.st.sendPngFile(f);
            } catch (IOException ex) {
                Logger.getLogger(SessionWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(extension.equals("jpg")){
            try {
                this.st.sendJpgFile(f);
            } catch (IOException ex) {
                Logger.getLogger(SessionWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_SendFilesButtonMouseClicked

    private void BorderImgMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BorderImgMouseDragged
        int cordX = evt.getXOnScreen();
        int cordY = evt.getYOnScreen();
        
        this.setLocation(cordX - mouseX,cordY - mouseY);
    }//GEN-LAST:event_BorderImgMouseDragged

    private void BorderImgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BorderImgMousePressed
        this.mouseX = evt.getX();
        this.mouseY = evt.getY();
    }//GEN-LAST:event_BorderImgMousePressed

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
    private javax.swing.JLabel BorderImg;
    private javax.swing.JTextArea ChatArea;
    private javax.swing.JTextArea MessageArea;
    private javax.swing.JButton SendButton;
    private javax.swing.JLabel SendFilesButton;
    private javax.swing.JLabel User;
    private javax.swing.JLabel exitButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel reduceButton;
    // End of variables declaration//GEN-END:variables
}
