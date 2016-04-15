public class Bullet extends Sprite{
	private String imgpath = "./assets/img/bulletShip.png";
	private OWNERTYPE owner; 
	private boolean outofScreen;
	private int dx;
	private int dy;

	int typeID;
	Bullet(int x,int y, OWNERTYPE owner,int dx,int dy,int typeID){
		this.typeID = typeID;
		this.dx=dx;
		this.dy=dy;
		outofScreen = false;
		this.owner=owner;
		if(owner==OWNERTYPE.ENEMY){
			imgpath = bulletInfo.getDownImgPath(typeID);
		}else {
			imgpath= bulletInfo.getUpImgPath(typeID);
		}	
		initImage(x,y,imgpath);
	}
	public OWNERTYPE getOwner(){
		return owner;
	}
	public void move(){
		if(owner==OWNERTYPE.PLAYER){
			addY(dy);
			addX(dx);
		}
		else{
			addY(dy);
			addX(dx);
		} 
	}

	public void addX(int deltax){

		this.x = this.x+deltax;

		if(x+width<=0 || x>=battleFieldWidth){
			outofScreen = true;
		}
		
	}

	public void addY(int deltay){

		this.y = this.y+deltay;

		if(y>=battleFieldHeight || y+height<= 0){
			outofScreen = true;			
		}
	
	}
	public boolean isOutOfScreen(){
		return outofScreen;
	}
	public int getDamage(){
		return bulletInfo.getDamage(typeID,owner);
	}
	public int getTypeID(){
		return typeID;
	}


}