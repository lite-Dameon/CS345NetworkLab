
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khyati
 */
public class MyJLabel extends JLabel {
    Image image;
    int WIDTH = 30, HEIGHT = 30;
    
    MyJLabel(){
    
    }
    
    public MyJLabel(String name)
    {
        if(name.equals("send")){
            this.setPreferredSize(new Dimension(WIDTH*5,HEIGHT*5));
        }
        else{
            this.setPreferredSize(new Dimension(WIDTH+10,HEIGHT+10));
        
        }
        this.setDoubleBuffered(true);
        this.setOpaque(false);
       
        this.image= new ImageIcon(getClass().getResource("/groupchat/images/"+name+".png")).getImage();
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
