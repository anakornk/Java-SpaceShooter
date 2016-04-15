import javax.swing.*;
import java.awt.*;

public class Player extends SpaceShip{
	static String imgpath = "./assets/img/playerShip3.png";
	public int dx;
	public int dy;
	static int maxHealth = 100;
	private int superAttackNum;
	private int bulletNum;
	private int bulletType=0;
	Player(int x,int y){
		initImage(x,y,imgpath,maxHealth);
		superAttackNum = 3;
		bulletNum = 1 ;
		dx = 0;
		dy = 0;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getSuperAttackNum(){
		return superAttackNum;
	}
	public int getBulletNum(){
		return bulletNum;
	}
	public void addHealth(int health){
		super.health+=health;
	}
	public void addBulletNum(int dBulletNum){
		bulletNum+=dBulletNum;
		if(bulletNum>5){
			bulletNum=5;
			superAttackNum++;
		}
	}
	public void updateSuperAttack(int dAttackNum){
		superAttackNum+=dAttackNum;
	}
	public void declineBulletNum(int dNum){
		if(bulletNum-dNum>0)bulletNum-=dNum;
		else bulletNum = 1;
	}
	public void addX(int deltax){

		this.x = this.x+deltax;
		//check if out of bounds
		if(x<0){
			this.x = 0;
		}
		if(x+width>battleFieldWidth){
			this.x = battleFieldWidth-width;
		}
		
		
	}
	public void addY(int deltay){
		this.y = this.y+deltay;
		//check if out of bounds
		if(y<0){
			this.y = 0;
		}
		if(y+height>battleFieldHeight){
			this.y = battleFieldHeight-height;
		}
	}
	public void changeBulletType(){
		bulletType=(bulletType+1)%5;
	}
	public int getBulletType(){
		return bulletType;
	}
}