
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khyati
 */

public class MyTextArea extends JTextArea
{
    Image image;
    int WIDTH = 400, HEIGHT = 250;
    public MyTextArea()
    {
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setDoubleBuffered(true);
        this.setOpaque(false);
        this.setLineWrap(true);
        
        this.setSize(new Dimension(100,100));
        this.image= new ImageIcon(getClass().getResource("/groupchat/images/letter.png")).getImage();
    }
    
    
    
    
    public void paintComponent(final Graphics g)
    {
        try
        {
        // Scale the image to fit by specifying width,height
        g.drawImage(image,0,0,getWidth(),getHeight(),this);
        super.paintComponent(g);
        }catch(Exception e){}
    }
}

