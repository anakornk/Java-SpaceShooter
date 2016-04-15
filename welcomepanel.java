
 

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import java.net.URL;

 

import javax.swing.ImageIcon;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Color;
import java.util.concurrent.locks.*;
import java.lang.Math;

public class welcomepanel extends JPanel implements MouseListener{
	JButton button1;
	boolean start_flag1=false;
	public int x, y;
	void drawBackground(Graphics g)
	{
	    Image img;
		
		try{
				img = ImageIO.read(new File("./assets/img/bg.png"));
				g.drawImage(img, 0, 0, 500, 700, this);
				
			}
			catch(IOException e){
				System.out.println("mb read error");
			}
		
	}
	
	
	public welcomepanel(){
		setLayout(null);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(500,700));
		addMouseListener(this);
		//this.addKeyListener(new KeyBoardListener());
        //gameStart();
		
	}
	
	void drawButton(Graphics g)
	{
		Image img;
		try{
				img = ImageIO.read(new File("./assets/img/button.png"));
				g.drawImage(img, 155, 200+300, 200, 50, this);
				
			}
			catch(IOException e){
				System.out.println("mb read error");
			}
		/*button1 = new JButton("Start",imageIcon1);
		add(button1);
		button1.setBounds(155,250,200,50);
        button1.addActionListener(new ActionListener()
		 {
			public void actionPerformed(ActionEvent e)
			{
				//System.out.println("BBBBBBB");
				start_flag1=true;
				
			}
		 }
		);*/
		
		
	}
	
	public void mousePressed(MouseEvent e){
    
		x = e.getX();
        y = e.getY();
		System.out.println("AAAAAA");
        if ((x >= 155)&&(x<=355)&&(y>=200+300)&&(y<=250+300)){System.out.println("BBBBBBBBB"); start_flag1=true;}
        
    }
	public void mouseExited(MouseEvent e){
        x = e.getX();
        y = e.getY();
        //mouseFlg = 3;
        //repaint();
    }
	 public void mouseRelease(MouseEvent e){
        x = e.getX();
        y = e.getY();
        
    }
	 public void mouseReleased(MouseEvent e){
        x = e.getX();
        y = e.getY();
        
    }
    public void mouseEntered(MouseEvent e){
        x = e.getX();
        y = e.getY();
       
    }
    
    public void mouseClicked(MouseEvent e){
         x = e.getX();
        y = e.getY();
    }
	
	public void paint(Graphics g){
		super.paint(g);
		drawBackground(g);
		drawButton(g);
		
	}
}