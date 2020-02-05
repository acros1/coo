/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import chatsystemproject.ClientThread;
import Database.Connect;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

/**
 *
 * @author Maeln
 */
public class pseudoWindow extends javax.swing.JFrame {

    /**
     * Creates new form pseudoWindow
     */
    private ClientThread clientThread = null;
    private String login = null;
    
    private Connect chatSystemDB = null;
    
    private int mouseX;
    private int mouseY;
    public pseudoWindow(String login, Connect chatSystemDB) {
        initComponents();
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1,1,1, Color.BLACK));
        this.login = login;
        this.chatSystemDB = chatSystemDB;
        //this.headerImg.setIcon(new ImageIcon("images/headerimg.jpg"));
    }
    
    private void creation_applicationWindow(){
        System.out.println(pseudo.getText());
        // Start clientThread with pseudo and login 
        this.clientThread = new ClientThread(pseudo.getText(), this.login);
        applicationWindow aW = new applicationWindow(this.clientThread, this.chatSystemDB);
        this.clientThread.setApplicationWindow(aW);
        new Thread(this.clientThread).start();
        
        System.out.println(this.clientThread.getMainUserPseudo());
        
        // Start application window
        aW.setVisible(true);
        aW.pack();
        aW.setLocationRelativeTo(null);
        aW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        exitButton = new javax.swing.JLabel();
        reduceButton = new javax.swing.JLabel();
        headerImg = new javax.swing.JLabel();
        ContentPanel = new javax.swing.JPanel();
        ValidButton = new javax.swing.JButton();
        pseudo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        headerPanel.setBackground(new java.awt.Color(255, 255, 255));

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

        headerImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/headerimg.jpg"))); // NOI18N
        headerImg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerImgMouseDragged(evt);
            }
        });
        headerImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerImgMousePressed(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(680, 680, 680)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(660, 660, 660)
                .addComponent(reduceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(headerImg)
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(exitButton)
            .addComponent(reduceButton)
            .addComponent(headerImg, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ContentPanel.setBackground(new java.awt.Color(255, 255, 255));

        ValidButton.setBackground(new java.awt.Color(153, 153, 153));
        ValidButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ValidButton.setForeground(new java.awt.Color(51, 102, 255));
        ValidButton.setText("Validate");
        ValidButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ValidButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ValidButtonMouseEntered(evt);
            }
        });
        ValidButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValidButtonActionPerformed(evt);
            }
        });

        pseudo.setBackground(new java.awt.Color(204, 204, 204));
        pseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pseudoActionPerformed(evt);
            }
        });
        pseudo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pseudoKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setText("pseudo :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("please choose a nickname ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Be careful it will identify you on the network");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("Alexandre Cros - Nedellec Maël © 2019-2020");

        javax.swing.GroupLayout ContentPanelLayout = new javax.swing.GroupLayout(ContentPanel);
        ContentPanel.setLayout(ContentPanelLayout);
        ContentPanelLayout.setHorizontalGroup(
            ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContentPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(191, 191, 191))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContentPanelLayout.createSequentialGroup()
                        .addComponent(ValidButton)
                        .addGap(302, 302, 302))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContentPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(250, 250, 250))))
            .addGroup(ContentPanelLayout.createSequentialGroup()
                .addGroup(ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContentPanelLayout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ContentPanelLayout.setVerticalGroup(
            ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContentPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(ContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(ValidButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pseudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pseudoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pseudoActionPerformed

    private void ValidButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValidButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ValidButtonActionPerformed

    private void ValidButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ValidButtonMouseClicked
        creation_applicationWindow();
    }//GEN-LAST:event_ValidButtonMouseClicked

    private void pseudoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pseudoKeyPressed
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER){
            creation_applicationWindow();
        }
    }//GEN-LAST:event_pseudoKeyPressed

    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitButtonMouseClicked

    private void reduceButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reduceButtonMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_reduceButtonMouseClicked

    private void ValidButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ValidButtonMouseEntered
        ValidButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_ValidButtonMouseEntered

    private void exitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseEntered
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_exitButtonMouseEntered

    private void reduceButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reduceButtonMouseEntered
        reduceButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_reduceButtonMouseEntered

    private void headerImgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerImgMousePressed
        this.mouseX = evt.getX();
        this.mouseY = evt.getY();
    }//GEN-LAST:event_headerImgMousePressed

    private void headerImgMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerImgMouseDragged
        int cordX = evt.getXOnScreen();
        int cordY = evt.getYOnScreen();
        
        this.setLocation(cordX - mouseX,cordY - mouseY);
    }//GEN-LAST:event_headerImgMouseDragged

    /**
     * @param args the command line arguments/
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
            java.util.logging.Logger.getLogger(pseudoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pseudoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pseudoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pseudoWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new pseudoWindow(null, this.chatSystemDB).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ContentPanel;
    private javax.swing.JButton ValidButton;
    private javax.swing.JLabel exitButton;
    private javax.swing.JLabel headerImg;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField pseudo;
    private javax.swing.JLabel reduceButton;
    // End of variables declaration//GEN-END:variables
}
