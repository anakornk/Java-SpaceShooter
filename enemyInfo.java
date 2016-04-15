public class enemyInfo{

	public static String getImgPath(int type){
		return imgPaths[type];
	}
	public static int getHealth(int type){
		return healths[type];
	}
	
	public static String[] imgPaths = {
		"./assets/img/enemyShip1.png",//enemyShip1
		"./assets/img/enemyShip2.png",//enemyShip2
		"./assets/img/enemyShip3.png",//enemyShip3
		"./assets/img/enemyShip4.png",//enemyShip4
		"./assets/img/enemyShip5.png",//enemyShip5
		"./assets/img/enemyShip6.png",//enemyShip6
		"./assets/img/enemyShipBoss.png",//enemyShipBoss
	};
	public static int[] healths = {
		25,//enemyShip1
		50,//enemyShip2
		100,
		200,
		300,
		400,
		1000,
	};
}