import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Color;
import java.util.concurrent.locks.*;
import java.lang.Math;


public class BattleField extends JPanel implements constant{
	boolean gameover_flag=false;
	private Player player;
	private Special special;

	private Thread drawThread;
	private Thread enemyCreationThread;
	private Thread effectsThread;
	private Thread enemyBulletCreationThread;
	private Thread playerBulletCreationThread;

	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;

	private ArrayList<Effects> effects;

	private Thread backgroundThread;
	private movingBackground mbInstance;
	//UI

	private ReadWriteLock enemyLock = new ReentrantReadWriteLock();
	private ReadWriteLock bulletLock = new ReentrantReadWriteLock();
	private ReadWriteLock effectLock = new ReentrantReadWriteLock();

	private int lastSpecialType = 0;
	private long lastSpecialTime = 0;
	public int  distance;
	private int  Ultnum=0;

	public BattleField(){
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(battleFieldWidth,battleFieldHeight));
		this.addKeyListener(new KeyBoardListener());
        distance = 0;
        gameStart();
		
	}

	void gameStart(){
		
		enemies = new ArrayList<Enemy>();
		effects = new ArrayList<Effects>();
		player = new Player(battleFieldWidth/2,battleFieldHeight);	
		bullets = new ArrayList<Bullet>();

		enemyCreationThread = new Thread(new enemyCreation());
		enemyCreationThread.start();

		playerBulletCreationThread = new Thread(new playerBulletCreation());
		playerBulletCreationThread.start();
		enemyBulletCreationThread = new Thread(new enemyBulletCreation());
		enemyBulletCreationThread.start();



		effectsThread = new Thread(new effectsTimer());
		effectsThread.start();

		mbInstance = new movingBackground("./assets/img/myBackground.png");
		backgroundThread = new Thread(mbInstance);
		backgroundThread.start();

		drawThread = new Thread(new draw());
		drawThread.start();


		
	}

	void drawPlayer(Graphics g){
		
		g.drawImage(player.getImage(),player.getX(),player.getY(),this);
		
		
	}
	void drawEnemy(Graphics g){
		enemyLock.readLock().lock();
		for(Enemy enemy:enemies){
			g.drawImage(enemy.getImage(),enemy.getX(),enemy.getY(),this);
		}
		enemyLock.readLock().unlock();
		//System.out.println("drawEnemy");
	}
	void drawBullet(Graphics g){
		//
		bulletLock.readLock().lock();
		for(Bullet bullet:bullets){
			g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
		}
		bulletLock.readLock().unlock();
	}

	void drawEffects(Graphics g){
		effectLock.readLock().lock();
		for(Effects effect: effects){
			g.drawImage(effect.getImage(),effect.getX(),effect.getY(),this);
		}
		effectLock.readLock().unlock();

	}

	void drawUI(Graphics g){
		//draw Healthbar size 150 17
		int len = (int)(150*((double)player.getHealth())/player.getMaxHealth());
		//System.out.println(((double)player.getHealth()));
		Image img_it;
		Image img_it2;
		//150 32 ziti 18
		try{
				img_it2 = ImageIO.read(new File("./assets/img/healthbar1.png"));
				g.drawImage(img_it2, 0, 0, 150, 17, this);
				
			}
			catch(IOException e){
				System.out.println("mb read error");
		}	
		try{
				img_it = ImageIO.read(new File("./assets/img/healbar.png"));
				g.drawImage(img_it, 0, 0, len, 17, 0, 0, len, 17,this);
				
			}
			catch(IOException e){
				System.out.println("mb read error");
			}

		
		//g.draw3Rect(0,0 ,len,10,true);
	}
	void drawBackground(Graphics g){
		//draw background
		
		g.drawImage(mbInstance.getImage(distance/10000),0,0, battleFieldWidth,mbInstance.dy, 0, battleFieldHeight-mbInstance.dy ,battleFieldWidth ,battleFieldHeight ,this);
		g.drawImage(mbInstance.getImage(distance/10000),0,mbInstance.dy,battleFieldWidth,battleFieldHeight,0,0,battleFieldWidth,battleFieldHeight-mbInstance.dy,this);
	}
	void drawSpecial(Graphics g){
		if(lastSpecialTime==0) lastSpecialTime = System.currentTimeMillis()-5000;
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastSpecialTime > 10000)
		{
			if(lastSpecialType==2){
				special = new Special( (int)(battleFieldWidth*Math.random()) , 0, 0);
				lastSpecialType = 0;
			}else if(lastSpecialType==0){
				special = new Special( (int)(battleFieldWidth*Math.random()) , 0, 1);
				lastSpecialType = 1;
			}else{
				special = new Special( (int)(battleFieldWidth*Math.random()) , 0, 2);
				lastSpecialType = 2;
			}
			
			//System.out.printf("AAAAAAAAAAAAA%d\n", 1 - lastSpecialType);
			lastSpecialTime = currentTime;
		}
		if(special!=null){
			g.drawImage(special.getImage(), special.getX(), special.getY(),this);
		}
	}
	
	void drawDistance(Graphics g){
		Image img_it;
		
		//150 32 ziti 18
		try{
				img_it = ImageIO.read(new File("./assets/img/distance.png"));
				g.drawImage(img_it, 350, 0, this);
			}
			catch(IOException e){
				System.out.println("mb read error");
			}
		
		
		Font font = new Font("Monospaced", Font.BOLD, 18);
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		g.drawString("DIST:"+distance/100,382,20);
	}
	
	void drawULT(Graphics g){
		//ziti 28
		Image img_it;
		
		
		try{
				img_it = ImageIO.read(new File("./assets/img/ult.png"));
				g.drawImage(img_it, 350, 32, this);
			}
			catch(IOException e){
				System.out.println("mb read error");
			}
		
		Font font = new Font("Monospaced", Font.BOLD, 18);
		g.setFont(font);
		g.drawString("ULT:"+player.getSuperAttackNum(),382,55);
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		drawBackground(g);
		drawPlayer(g);
		drawEnemy(g);
		drawBullet(g);
		drawEffects(g);
		drawUI(g);
		drawSpecial(g);
		drawDistance(g);
		drawULT(g);
		//drawULT(g);

	}
	void updateDistance()
	{
		distance++;
	}

	void Update(){
		//change to x+dx,y+dy

		player.addX(player.dx);
		player.addY(player.dy);

		if(special!=null){
			special.move();
		}

		updateBEF();//BEF = Bullet Enemy Effects
		checkSpecial();//check if we need to generate special and generate it if needs
        updateDistance();
		if (player.health<=0)
		{
			gameover_flag=true;
		}
	}

	//BEF = Bullet Enemy Effects
	/*void setDistance(long x)
	{
		distance=x/100;
	}*/
	void updateBEF(){

		bulletLock.writeLock().lock();
		enemyLock.writeLock().lock();		
		effectLock.writeLock().lock();
		
		Rectangle rPlayer = new Rectangle(player.getX(),player.getY(),player.getImageWidth(),player.getImageHeight());		


		//remove effects
		ArrayList<Effects> removeEffects = new ArrayList<Effects>();
		for(Effects effect:effects){
			if(effect.time <=0){
				removeEffects.add(effect);
			}
		}
		effects.removeAll(removeEffects);

		//move bullet
		//remove bullet if out of screen or collision
		ArrayList<Bullet> removeBullet = new ArrayList<Bullet>();
		for(Bullet bullet: bullets){
			
			//move the bullet
			bullet.move();
			//if bullet is already outofscreen ->remove it
			if(bullet.isOutOfScreen()){
				removeBullet.add(bullet);
				continue;
			}
			if(bullet.getOwner()==OWNERTYPE.ENEMY&&bullet.isIntersectWith(rPlayer)){
				//bullet collides with player
				//System.out.println("enemy collides with player");
				
				//bullet collision effects
				int typeID = bullet.getTypeID();
					switch(typeID){
						case 0:
						effects.add(new effectLaser(bullet.getX(),bullet.getY(),1));
						break;
						case 1:
						effects.add(new effectLaser(bullet.getX(),bullet.getY(),1));
						break;
						case 2:
						effects.add(new effectOrange(bullet.getX()+5,bullet.getY(),6));
						break;
						case 3:
						effects.add(new effectBoom(bullet.getX(),bullet.getY()+100,11));
						break;
						case 4:
						effects.add(new effectBoom(bullet.getX()+25,bullet.getY()+100,11));
						break;

					}
				
				
				removeBullet.add(bullet);
				player.minusHealth(bullet.getDamage());
				player.declineBulletNum(1);
			}else if(bullet.getOwner()==OWNERTYPE.PLAYER){
				//bullet belongs to owner
				//check if collides with any of the enemy
				for(Enemy enemy: enemies){
					Rectangle rEnemy = new Rectangle(enemy.getX(),enemy.getY(),enemy.getImageWidth(),enemy.getImageHeight());
					if(bullet.isIntersectWith(rEnemy)){
						//bullet collides with the enemy
						enemy.minusHealth(bullet.getDamage());
						
						//effects.add(new effectLaser(bullet.getX(),bullet.getY(),1));
						//effects.add(new effectBlue(bullet.getX(),bullet.getY(),6));
						
						int typeID = bullet.getTypeID();
						switch(typeID){
							case 0:
							effects.add(new effectBlue(bullet.getX(),bullet.getY(),6));
							break;
							case 1:
							effects.add(new effectOrange(bullet.getX(),bullet.getY(),6));
							break;
							case 2:
							effects.add(new effectOrange(bullet.getX(),bullet.getY(),6));
							break;
							case 3:
							effects.add(new effectBoom(bullet.getX(),bullet.getY(),11));
							break;
							case 4:
							effects.add(new effectBoom(bullet.getX(),bullet.getY(),11));
							break;

						}

						removeBullet.add(bullet);
					}
				}
			}

		}
		bullets.removeAll(removeBullet);


		//move enemy
		//remove enemy if out of screen or if enemy collides with the player
		ArrayList<Enemy> removeEnemy = new ArrayList<Enemy>();
		for(Enemy enemy: enemies){
			
			//move the enemy
			enemy.move();
			//if enemy is already outofscreen->remove it
			if(enemy.isOutOfScreen() || enemy.isDead()){
				removeEnemy.add(enemy);
				continue;
			}

			if(enemy.isIntersectWith(rPlayer)){
				//enemy collides with player
				//System.out.println("enemy collides with player");
				//(new Thread(new Explosions(enemy.getX(),enemy.getY(),1000))).start();
				effects.add(new effectLaser(enemy.getX(),enemy.getY(),1));
				removeEnemy.add(enemy);
				player.minusHealth(10);
				player.declineBulletNum(1);
			}
		}
		enemies.removeAll(removeEnemy);
		effectLock.writeLock().unlock();
		enemyLock.writeLock().unlock();
		bulletLock.writeLock().unlock();
	}
	void checkSpecial(){
		if(special!=null)
		{
			if( special.outOfRange() )
			{
				//System.out.println("AAAAAA");
				special=null;
				return ;
			}

			Rectangle rPlayer = new Rectangle(player.getX(),player.getY(),player.getImageWidth(),player.getImageHeight());
			if(special.isIntersectWith(rPlayer))
			{
				effects.add(new effectLaser(special.getX(), special.getY(),1));
				if(special.getType()==0){
					player.addHealth(50);
					special=null;
				}else if (special.getType()==1){
					player.addBulletNum(1);
					special=null;
				}else{
					player.changeBulletType();
					special=null;
				}
			}
		}	

	}
	//subclass for drawThread
	class draw implements Runnable{
		public void run(){
		long lastTime;
		long diff;
		long sleepTime;
		//sleeptime - the time between loops
		lastTime = System.currentTimeMillis();

		while(true){
			//setDistance(lastTime);
			
			Update();
			repaint();
			
			//make sure that the time per loop is the same
			diff = System.currentTimeMillis()-lastTime;
			sleepTime = 5 - diff;
			//System.out.println(diff);

			if(sleepTime<0){
				sleepTime = 4;
			}

			try{
				Thread.sleep(sleepTime);
			}
			catch(InterruptedException e){
				System.out.println("interrupted");
			}
			
			lastTime = System.currentTimeMillis();
		}
	}
	}

	
	//sub class for enemyCreationThread
	class enemyCreation implements Runnable{
		public void run(){
			//create new enemy every 2 second
			long sleepTime = 4000;
			Random rnd = new Random();
			while(true){
				//create enemies
				enemyLock.writeLock().lock();
				//rnd.nextint(3)-random enemyship creation
				enemies.add(new Enemy(rnd.nextInt(battleFieldWidth-50)+25,0,distance));
				enemyLock.writeLock().unlock();
				try{
					Thread.sleep(sleepTime);
				}
				catch(InterruptedException e){
					System.out.println("enemyCreation sleep interupped");
				}
				//System.out.println("enemyCreation");
				
			}
		}
	}

	//sub class for bulletCreationThread
	class enemyBulletCreation implements Runnable{
		public void run(){
			//create new bullet every 0.5 second
			while(true){
				bulletLock.writeLock().lock();
				enemyLock.readLock().lock();
				for(Enemy enemy:enemies){ //iterator enemies to generate bullet for every enemy
					
					/*****************************************/
					if(enemy.getTypeID()==0){
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,1) );
					}else if(enemy.getTypeID()==1){
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,1) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,1) );
					
					}else if(enemy.getTypeID()==2){
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,10,2) );	
					
					}else if(enemy.getTypeID()==3){
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 10, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,-1,2,1) );
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,2) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 10, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,1,2,1) );

					}else if(enemy.getTypeID()==4){
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,-1,2,1) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,3) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,1,2,1) );
					}else if(enemy.getTypeID()==5){
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,0) );
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 7, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,0) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,0) );
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 7, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,0) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,0) );
					}else if(enemy.getTypeID()==6){
						//BOSS
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 200, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,4) );
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 - 75, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,3) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 -35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,1) );
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,4) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 35, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,1) );
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 75, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,3) );	
						bullets.add( new Bullet(enemy.getX()+enemy.getImageWidth()/2 + 200, enemy.getY()+enemy.getImageHeight(), OWNERTYPE.ENEMY,0,2,4) );	
					}

					/*****************************************/
				}
				enemyLock.readLock().unlock();
				bulletLock.writeLock().unlock();//need to make sure the order

				try{
					Thread.sleep(sleepTimeOfEnemyBulletCreation);
				}
				catch(InterruptedException e){
					System.out.println("bulletCreation sleep interupped");
				}
				//System.out.println("bulletCreation");
			}
		}
	}
	class playerBulletCreation implements Runnable{
		public void run(){
			//creat new bullet every 0.3 second
			while(true){

				bulletLock.writeLock().lock();
				int x = player.getX()+player.getImageWidth()/2;
				int y = player.getY();

				//judge two kinds of bullet layout
				if(player.getBulletNum()%2==0){ //the bullet number is even => ||
					for(int i = 0 ; i< player.getBulletNum() ; i+=2){
						bullets.add( new Bullet( x+(i+1)*10, y , OWNERTYPE.PLAYER,0,-2,player.getBulletType() ) );
						bullets.add( new Bullet( x-(i+1)*10, y , OWNERTYPE.PLAYER,0,-2,player.getBulletType()) );
					}
				}else{ // the bullet number is odd => |||
					bullets.add( new Bullet( x, y , OWNERTYPE.PLAYER,0,-2,player.getBulletType()) );
					for(int i = 1 ; i< player.getBulletNum() ; i+=2){
						bullets.add( new Bullet( x+(i+1)*10+5, y , OWNERTYPE.PLAYER,0,-2,player.getBulletType()) );
						bullets.add( new Bullet( x-(i+1)*10-5, y , OWNERTYPE.PLAYER,0,-2,player.getBulletType()) );
					}
				}
				
				bulletLock.writeLock().unlock();
				try{
					Thread.sleep(sleepTimeOfPlayerBulletCreation);
				}
				catch(InterruptedException e){
					System.out.println("bulletCreation sleep interupped");
				}
				//System.out.println("bulletCreation");
			}
		}
	}
	//subclass for Effects
	class effectsTimer implements Runnable{
		public void run(){
			while(true){
				effectLock.writeLock().lock();	
				for(Effects effect: effects){
					effect.minusOne();
				}
				effectLock.writeLock().unlock();
				try{
					Thread.sleep(50);
				}
				catch(InterruptedException e){
					System.out.println("effects timer sleep interupped");
				}
			}
		}
	}
	//subclass for moving background
	class movingBackground implements Runnable{
		private Image img1;
		private Image img2;
		private Image img3;
		private Image img4;
		private Image img5;
		
		public int dy;
		movingBackground(String imgpath){
			dy = 0;
			
			try{
				
				img1 = ImageIO.read(new File("./assets/img/background1.png"));
			    img2 = ImageIO.read(new File("./assets/img/background2.png"));
				img3 = ImageIO.read(new File("./assets/img/background3.png"));
				img4 = ImageIO.read(new File("./assets/img/background4.png"));
				img5 = ImageIO.read(new File("./assets/img/background5.png"));
			}
			catch(IOException e){
				System.out.println("mb read error");
			}
			
		}
		public Image getImage(int x){
			if (x==0) return img1;
			if (x==1) return img2;
			if (x==2) return img3;
			if (x==3) return img4;
			if (x==4) return img5;
			return img5;
		}
		public void run(){
			while(true){
				dy++;
				//256 is image height
				if(dy>battleFieldHeight){
					dy=0;
				}
			//System.out.println(dy+"12");
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
					
			}
			}
			
		}	
	}
	//subclass
	class KeyBoardListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_UP){
				if(player.dy >-5){
					player.dy += -1;
				}
				
			}else if(keycode == KeyEvent.VK_LEFT){
				if(player.dx >=0){
					player.dx = -2;
				}
				if(player.dx >-5){
					player.dx += -1;
				}
			}else if(keycode == KeyEvent.VK_DOWN){
				if(player.dy <5){
					player.dy += 1;
				}
			}else if(keycode == KeyEvent.VK_RIGHT){
				if(player.dx <=0){
					player.dx = 2;
				}
				if(player.dx <5){
					player.dx += 1;
				}
			}else if(keycode == KeyEvent.VK_SPACE){
				//super attack
				superAttack();
			}
		//	System.out.println("press");
		}
		public void keyReleased(KeyEvent e){
			//dx + dy set to zero
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_UP){
				player.dy=0;
			}else if(keycode == KeyEvent.VK_LEFT){
				player.dx=0;
			}else if(keycode == KeyEvent.VK_DOWN){
				player.dy=0;
			}else if(keycode == KeyEvent.VK_RIGHT){
				player.dx=0;
			}
		//	System.out.println("release");
		}
		void superAttack(){ 
			//judge if it can make super attack
			if(player.getSuperAttackNum()<=0)return;

			//enemies disappear and make effects
			enemyLock.writeLock().lock();
			for(Enemy enemy:enemies){
				effectLock.writeLock().lock();
				effects.add(new effectLaser(enemy.getX(),enemy.getY(),3));
				effectLock.writeLock().unlock();
			}
			enemies.clear();
			enemyLock.writeLock().unlock();

			//enemy's bullet disappear and make effects
			bulletLock.writeLock().lock();
			ArrayList<Bullet> superBulletRemove = new ArrayList<Bullet>();
			for(Bullet bullet:bullets){
				if(bullet.getOwner()==OWNERTYPE.ENEMY){
					superBulletRemove.add(bullet);
					effectLock.writeLock().lock();
					effects.add(new effectLaser(bullet.getX(),bullet.getY(),3));
					effectLock.writeLock().unlock();
				}
			}
			bullets.removeAll(superBulletRemove);
			bulletLock.writeLock().unlock();

			//make effects belonging to super attack
			effectLock.writeLock().lock();
			for(int x = 0 ; x <  battleFieldWidth ; x += 50){
				for(int y = 0 ; y < battleFieldHeight ; y +=50 ){
					effects.add( new SuperExplosions(x, y, 2) );
				}
			}
			effectLock.writeLock().unlock();

			//update super attack number
			player.updateSuperAttack(-1);
		}
	} 

}
