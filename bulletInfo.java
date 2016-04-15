public class bulletInfo{
	public static String getUpImgPath(int type){
		return bulletUpImgPaths[type];
	}
	public static String getDownImgPath(int type){
		return bulletDownImgPaths[type];
	}
	public static int getDamage(int type,OWNERTYPE owner){
		if(owner == OWNERTYPE.ENEMY){
			return damage[type][1];
		}else{
			return damage[type][0];
		}
		
	}
	public static String[] bulletUpImgPaths = {
		"./assets/img/bulletUp0.png",
		"./assets/img/bulletUp1.png",
		"./assets/img/bulletUp2.png",
		"./assets/img/bulletUp3.png",
		"./assets/img/bulletUp4.png",		
	};
	public static String[] bulletDownImgPaths = {
		"./assets/img/bulletDown0.png",
		"./assets/img/bulletDown1.png",
		"./assets/img/bulletDown2.png",
		"./assets/img/bulletDown3.png",
		"./assets/img/bulletDown4.png",
	};
	public static int[][] damage = {
		{25,10},//bullet 1
		{50,10},//bullet 2
		{100,10},//bullet 3
		{200,10},//bullet 4
		{300,20},//bullet 5
	};

}