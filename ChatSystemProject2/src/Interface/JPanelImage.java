/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author Maeln
 */
public class JPanelImage extends JPanel {
    private Image img;
    
    public JPanelImage(Image img){
        this.img = img;
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(img, 0, 0, null);
    }
}
