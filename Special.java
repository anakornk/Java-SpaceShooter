public class Special extends Sprite{
	private String imgpath ;
	private int type ;//012
	private int yStore ;
	Special(int x,int y,int type){
		this.type=type;
		yStore=0;
		if(type==0){imgpath= "./assets/img/medicine.png";}
		else if(type==1){imgpath= "./assets/img/ammunition.png";}
		else {imgpath= "./assets/img/changebullet.png";}
		initImage(x,y,imgpath);
	}
	public Boolean outOfRange(){
		if(x>battleFieldWidth||y>battleFieldHeight)return true;
		else return false;
	} 
	public void move(){
		//before - super.addY(1-yStore);
		yStore = 1 - yStore ;
		y = y + yStore;
		
		//System.out.println(yStore);
	}

	public int getType(){
		return type;
	}

}
