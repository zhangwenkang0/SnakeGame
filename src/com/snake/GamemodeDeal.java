package com.snake;

import java.awt.Color;
import java.awt.Graphics;

//游戏模式处理
public class GamemodeDeal {
	private GamePanel gamePanel;
	public static Snake snake1 = null;
	public static Snake snake2 = null;
	public static Egg egg = null;
	private static ClassesDeal classesDeal = null;
	public static int snakeLength1;// 蛇1的长度
	public static int snakeLength2;// 蛇2的长度
	public static int life1;// 蛇1生命
	public static int life2;// 蛇2生命

	public GamemodeDeal(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	// 实例化处理 在GamePanel出调用
	public static void instantiate() {

		snake1 = new Snake();
		snake2 = new Snake(GamePanel.gameMode);
		egg = new Egg(snake1, snake2);
		classesDeal = new ClassesDeal(snake1, snake2);
		snakeLength1 = snake1.snakeVc.size();// 蛇1的长度
		snakeLength2 = snake2.snakeVc.size();// 蛇2的长度
		life1 = snake1.life;
		life2 = snake2.life;
	}

	// 重绘方法
	public void paint(Graphics g) {

		// 画蛇
		snake1.drawSanke(g);
		// if (GamePanel.gameStates == GameStates.START) {

		// }
		// 吃了自己的尾死亡 重绘蛇头 因美观原因 避免蛇尾覆盖了蛇头
		if (snake1.isLive == false) {
			g.setColor(Color.red);
			g.fill3DRect(snake1.getHead().getX(), snake1.getHead().getY(), 30, 30, false);
		}
		if (GamePanel.gameMode == 2) {
			// 画蛇
			snake2.drawSanke(g);
			// 吃了自己的尾死亡 重绘蛇头 因美观原因 避免蛇尾覆盖了蛇头
			if (snake2.isLive == false) {
				g.setColor(Color.blue);
				g.fill3DRect(snake2.getHead().getX(), snake2.getHead().getY(), 30, 30, false);
			}
		}
		// 蛋死亡则生成蛋
		if (egg.isLive == false) {
			egg.setRandom();
		}
	}

	// 线程里的处理
	public void threadDeal() {

		snakeLength1 = snake1.snakeVc.size();

		// 蛇存活则运动
		if (GamePanel.gameStates != GameStates.GAMEOVER) {
			snake1.Snakemove();
		}
		// 有新的方向 则进行方向处理
		if (snake1.newdirVc.size() > 0) {
			snake1.changeDir(snake1.newdirVc.get(0));
		}
		// 没有蛋了 即蛋被吃了
		if (!egg.haveEgg()) {
			gamePanel.eatEgg();
		}
		if (GamePanel.gameMode == 2) {
			// 蛇存活则运动
			if (GamePanel.gameStates != GameStates.GAMEOVER) {
				snake2.Snakemove();
			}
			// 有新的方向 则进行方向处理
			if (snake2.newdirVc.size() > 0) {
				snake2.changeDir(snake2.newdirVc.get(0));
			}
			snakeLength2 = snake2.snakeVc.size();
			life1 = snake1.life;
			life2 = snake2.life;
		}
		// 死亡处理
		classesDeal.dieDeal();

	}

	// 吃蛋处理
	public void eatEgg() {
		if (GamePanel.gameMode == 1) {
			if (snake1.isEategg == true) {
				snake1.addSnakenode();
				snake1.isEategg = false;
			}
		}
		if (GamePanel.gameMode == 2) {
			if (snake1.isEategg == true) {
				snake1.addSnakenode();
				snake2.life -= 1;
				snake1.isEategg = false;
			}
			if (snake2.isEategg == true) {
				snake2.addSnakenode();
				snake1.life -= 1;
				snake2.isEategg = false;
			}
		}
		if (MySnakeGame.isHaveSound == true) {
			MySnakeGame.gameSound.musicPlay();
		}

	}

	// 重新开始游戏处理
	public void restart() {
		snake1 = new Snake();
		snakeLength1 = snake1.snakeVc.size();
		if (GamePanel.gameMode == 2) {
			snake2 = new Snake(GamePanel.gameMode);
			snakeLength2 = snake2.snakeVc.size();
			life1 = snake1.life;
			life2 = snake2.life;
		}

		egg = new Egg(snake1, snake2);
		classesDeal = new ClassesDeal(snake1, snake2);
	}

}
