import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements constant{
	private JPanel gamePanel;
	private JMenuBar menuBar;
	private JMenuItem aboutItem;
	private JMenu about;

	public Game(){			
		initGUI();
	}
    	
	void initGUI(){
		setTitle("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setSize(screenWidth,screenHeight);
		setResizable(false);


		//menubar
		ImageIcon imgIcon = new ImageIcon("./assets/img/aboutBG.png");

		aboutItem = new JMenuItem(imgIcon);
		about = new JMenu("About");

		about.add(aboutItem);
		
		menuBar = new JMenuBar();
		menuBar.add(about);

		//gamepanel + battlefield
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(1,1));

		welcomepanel wl= new welcomepanel();
		gamePanel.add(wl);

		//add to frame
		this.add(menuBar,BorderLayout.NORTH);
		this.add(gamePanel,BorderLayout.CENTER);
		pack();
		setVisible(true);
//wl.requestFocus();
		
		//work (wl.start_flag1);
		while (wl.start_flag1==false)
		{
			try{
				Thread.sleep(5);
			}
			catch(InterruptedException e){
				System.out.println("interrupted");
			}

		}
		
		gamePanel.remove(wl);
		//gamePanel.doLayout();
		gamePanel.validate();
		gamePanel.repaint();
		BattleField  bf = new BattleField();
		gamePanel.add(bf);
		bf.requestFocus();
		gamePanel.validate();
		gamePanel.repaint();

		
		
		while (true)
		{
			//work (bf.gameover_flag);
			while (bf.gameover_flag==false)
			{
				try{
					Thread.sleep(5);
				}
				catch(InterruptedException e){
					System.out.println("interrupted");
				}
			}
			gamePanel.remove(bf);
			gamePanel.validate();
			gamePanel.repaint();
			GameoverPanel Gp = new GameoverPanel();
			gamePanel.add(Gp);
			gamePanel.validate();
			gamePanel.repaint();

			//work(Gp.restart_flag1);
			while (Gp.restart_flag1==false)
			{
				try{
					Thread.sleep(5);
				}
				catch(InterruptedException e){
					System.out.println("interrupted");
				}
			}
		
			gamePanel.remove(Gp);
			gamePanel.validate();
			gamePanel.repaint();
			bf = new BattleField();
			gamePanel.add(bf);
			bf.requestFocus();
			gamePanel.validate();
			gamePanel.repaint();
		}
	}

	public static void main(String args[]){
    		Game g = new Game();
	}

}