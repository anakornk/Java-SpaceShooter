how to run:
	javac Game.java
	java Game

how to operate:
	using up,down,left,right to move
	using space to make a great attack
	eating medicine can increase your health
	eating ammunition can increase your bullet number


the file including:
	assets/img/*
	Game.java : where main exists
	BattleField.java : whera arange the game
	Bullet.java
	bulletinfo.java
	constant.java
	effectblue.java
	effectBoom.java
	effectLaser.java
	effectOrange.java
	Effects.java
	Enemy.java
	enemyinfo.java
	GameoverPanel.java
	OWNERTYPE.java
	Player.java
	SpaceShip.java
	Special.java
	Sprite.java
	SuperExplosions.java
	welcomepanel.java
	REANME.MD : this file

notice for developer
	the order for lock is bullets->enemies->effects

log 1 - fzx
	add great attack
	add multi bullet
	add medicine & ammunition
	add REANME.MD
log 2 - tata
	1) check for out of screen
	delete the bullets/enemies if out of screen 
	addX addY in SpaceShip class - check for out of bounds
	2) bullet.move() moved from Update() to UpdateBEF() -> no need to loop twice
	3) special addY -> special move
	4) add bullet damage , damage parameter in constructor
	5) collision between bullets and enemies
	6) if health below 0 enemy is dead ->remove it
	7) defaultBulletDamage=25 in constant.java
		starting health of enemy is 50
		takes 2 bullets to kill the enemy
	8) moving background
	9) classes restructured
	sprite->Spaceship->enemy
					 ->player
		  ->effects->explosions
		  ->bullet
	10) ystore
		before: super.addY(1-yStore);
				yStore = 1-yStore;
		after: 		
				yStore = 1 - yStore ;
				y = y + yStore;
	11) slow down enemy creation + enemy speed
	12) enemyInfo.java -> information about the enemy
		includes enemy imgpath(s) + health
	13) battlefield line 217 change to explosiontest (animation explosion) (not done yet)
	14) lastSpecialType ? edited but not sure if correct
log 3 -fzx
	change some paraments into constant to change for other developer eg the frequency of bullet
	add the function that : if player' health is reduced for some reason the bulletNumber of it will decline 1
	restrict the bulletNumber to 5 and if it goes on eating AMMUNITION the superAttack number will increase 1
	add the super attack of enemy's boss which send bullet to three different direction
	make function move to unify the moving
	complete enemy's type making enemy boss not moving
log 4 -tata
	add Effects usage;
	effects.add(new effectLaser(bullet.getX(),bullet.getY(),1));
	effects.add(new effectBlue(bullet.getX(),bullet.getY(),6));
	effects.add(new effectOrange(bullet.getX(),bullet.getY(),6));
	effects.add(new effectBoom(bullet.getX(),bullet.getY(),11));
log 5 -fzx
	change the type of bullet while eating changebullet
	change boundary of generating boss
	
