import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class effectLaser extends Effects{
	static String[] imgpath = {
		"./assets/img/laserGreenShot.png",
	};	

	effectLaser(int x,int y,int time){
		super(x,y,imgpath[0],time);

	}
	@Override 
	public int getX(){
		return x;
	}
	@Override
	public int getY(){
		return y+height/2;
	}

}