import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Effects extends Sprite{

	protected int time;

	Effects(int x,int y,String imgpath,int time){
		initImage(x,y,imgpath);
		this.time = time;

		
	}
	public void minusOne(){
		time--;
	}
	public int getTime(){
		return time;
	}
}