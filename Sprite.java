import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
public abstract class Sprite implements constant{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Image img;





	Sprite(){

	}
	void initImage(int startX,int startY,String imgPath){

		BufferedImage bImg;
		try{
			bImg = ImageIO.read(new File(imgPath));
			width = bImg.getWidth();
			height = bImg.getHeight();
			img = bImg;
			//startX - middle of the image
			//startY - top
			//x y is location of the topleft corner of the image
			x = startX-width/2;
			y = startY-height;
		}catch(IOException e){
			//System.out.println()
		}
	}

	public Image getImage(){
		return img;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getImageWidth(){
		return width;
	}
	public int getImageHeight(){
		return height;
	}
	public boolean isIntersectWith(Rectangle r){
		//not done yet
		boolean check = (new Rectangle(x,y,width,height)).intersects(r);
		return check;
	}



}