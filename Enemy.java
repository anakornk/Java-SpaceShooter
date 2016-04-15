import java.util.*;

public class Enemy extends SpaceShip{

	static int boundary=500;
	boolean outofScreen;

	int reduceSpeed = 1;
	//boolean isBoss;
	private int typeID;
	Enemy(int x,int y,int distance){
		dx = 0;
		dy = 1;
		outofScreen = false;
		//type - > imgpath - > health
		int type = 0;
		Random rnd = new Random();
		distance=distance/100;
		
		if(distance<100)type=rnd.nextInt(2);
		else if(distance<200)type=rnd.nextInt(3);
		else if(distance<300)type=rnd.nextInt(4);
		else type=rnd.nextInt(6);
		
		if(distance>boundary){
			boundary+=500;
			x = battleFieldWidth/2;
			type = 6;
			dy=0;
		}
		typeID=type;
		initImage(x,y,enemyInfo.getImgPath(type),enemyInfo.getHealth(type));
		reduceSpeed = 0;
	}
	public void addX(int deltax){

		this.x = this.x+deltax;
		
	}
	public void addY(int deltay){
		//reduce speed of enemy by 2
		reduceSpeed = (reduceSpeed + 1)%2;
		if(reduceSpeed ==0){
			this.y = this.y+deltay;

			if(y>=battleFieldHeight){
			outofScreen = true;
			}
		}
	}
	public boolean isOutOfScreen(){
		return outofScreen;
	}
	void move(){
		if(dy==0){
			if(y<40)addY(1);
		}
		else{
			addY(dy);
			addX(dx);
		}
	}
	public int getTypeID(){
		return typeID;
	}

}