Running the game (static public void main is in Game.java):

  javac Game.java

  java Game
  

Controls:

  arrow keys - control the spaceship

  spacebar - special attack

**Java 飞机大战（DISTANCE,UTL）**

一、成员：

陈继业 1400094804 S67

付振新 1400012736 S19

王佳麟 1400012741 S20

二、操作：

左右上下来控制飞船

空格 – 大招

三、功能集锦:

吃弹药箱可以增加每次发子弹的个数

吃&quot;改变子弹&quot;可以改变所发射子弹的种类

吃医疗包可以增加血量

对上下移动的速度做了特殊处理，更贴合实际，有加速的感觉

背景是动态背景，而且随着游戏进行，背景会进行更换

敌方有不同的种类，而且敌方存在大boss

我方有大招，可以干掉屏幕内所有敌人以及子弹

四、游戏界面：

![picture1](https://cloud.githubusercontent.com/assets/8156002/18005317/79f953b8-6bc3-11e6-80fc-c65ea1ca9cfa.png)
![picture2](https://cloud.githubusercontent.com/assets/8156002/18005316/79ec8d90-6bc3-11e6-92cf-9185a4d4263c.png)
五、设计思想：

通过EventListener来监听用户对键盘的操作，左右上下控制飞船

多线程 – 用来画，效果，更新飞船的位置，计算是否有碰撞，生成新敌人的飞船

为了防止DeadLock 采用了锁机制

检查碰撞 – 认为每个飞船是一个长方形 检验是否有交叉

六、类的继承关系
![picture3](https://cloud.githubusercontent.com/assets/8156002/18005315/79e94e14-6bc3-11e6-8477-72d93adf8ad4.png)

七、有三个类用来记录常量

enemyInfo.java – 记录关于敌人飞船的信息 如生命值，图片

bulletInfo.java – 记录关于各种子弹的信息 如伤害力，图片

constant.java – 关于游戏界面的信息 如屏幕大小

八、UI

采用了JFrame,JPanel,MenuBar,MenuItem

游戏的绘画都在BattleField JPanel上 （BattleField在gamePanel Jpanel上）
![picture4](https://cloud.githubusercontent.com/assets/8156002/18005314/79e15d62-6bc3-11e6-8dc3-0bf20474cab8.png)
