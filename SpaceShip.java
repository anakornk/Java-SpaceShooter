import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
public abstract class SpaceShip extends Sprite{
	protected int health;
	protected boolean dead;
	protected int dx;
	protected int dy;
	SpaceShip(){

	}
	void initImage(int startX,int startY,String imgPath,int health){
		this.health = health;
		dead = false;
		initImage(startX,startY,imgPath);
	}


	public boolean isIntersectWith(Rectangle r){
		//not done yet
		boolean check = (new Rectangle(x,y,width,height)).intersects(r);
		return check;
	}
	public int getHealth(){
		return health;
	}
	public void minusHealth(int x){

		health = health - x;
		if(health <= 0){
			health=0;
			dead = true;
		}
		
	}

	public boolean isDead(){
		return dead;
	}
	abstract public void addX(int deltax);
	abstract public void addY(int deltaY);

}