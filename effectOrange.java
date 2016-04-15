import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class effectOrange extends Effects{
	static String[] imgpath = {
		"./assets/img/OrangeBulletExplo1.png",
		"./assets/img/OrangeBulletExplo2.png",
		"./assets/img/OrangeBulletExplo3.png",
		"./assets/img/OrangeBulletExplo4.png",
		"./assets/img/OrangeBulletExplo5.png",
		"./assets/img/OrangeBulletExplo6.png",
	};	

	private int currentFrame;
	private int frameAmount;
	effectOrange(int x,int y,int time){
		super(x,y,imgpath[0],time);
		currentFrame = 0;
		frameAmount = effectBlue.imgpath.length;
	}

	@Override
	public void minusOne(){
		time--;
		currentFrame = (currentFrame+1)%frameAmount;
		setImagebyFrame(currentFrame);
	}

	public void setImagebyFrame(int frame){
		try{
			BufferedImage bImg = ImageIO.read(new File(imgpath[frame]));
			width = bImg.getWidth();
			height = bImg.getHeight();
			Image tmp = bImg;
			img = tmp;
		}
		catch(IOException e){

		}
		
	}
	@Override 
	public int getX(){
		return x - width/2;
	}
	@Override
	public int getY(){
		return y - height/2;
	}


}