import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class effectBoom extends Effects{
	static String[] imgpath = {
		"./assets/img/Explo__000.png",
		"./assets/img/Explo__001.png",
		"./assets/img/Explo__002.png",
		"./assets/img/Explo__003.png",
		"./assets/img/Explo__004.png",
		"./assets/img/Explo__005.png",
		"./assets/img/Explo__006.png",
		"./assets/img/Explo__007.png",
		"./assets/img/Explo__008.png",
		"./assets/img/Explo__009.png",
		"./assets/img/Explo__010.png",
		

	};	

	int currentFrame;
	int frameAmount;
	effectBoom(int x,int y,int time){
		super(x,y,imgpath[0],time);
		currentFrame = 0;
		frameAmount = effectBoom.imgpath.length;
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
		return x;
	}
	@Override
	public int getY(){
		return y;
	}


}