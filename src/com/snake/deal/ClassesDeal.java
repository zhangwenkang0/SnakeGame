package com.snake.deal;

import com.snake.enums.TwoModel;
import com.snake.game.Snake;
import com.snake.swing.GamePanel;
import com.snake.enums.GameStatus;
import com.snake.swing.MySnakeGame;

/**
 * 游戏模式级别处理
 */
public class ClassesDeal {
	Snake snake1;
	Snake snake2;

	public ClassesDeal(Snake snake1, Snake snake2) {
		this.snake1 = snake1;
		this.snake2 = snake2;
	}

	// 游戏死处理别
	public void dieDeal() {
		switch (GamePanel.classes) {
			// 慢速
			case SIMPLE:
				// 单人 新手
				low();
				break;
			// 中速
			case MIDDLE:
				// 单人 普通
				middle(snake1);
				// 双人模式 激斗模式
				if (GamePanel.twoModel == TwoModel.TWO_1) {
					robFood();
				}
				if (GamePanel.twoModel == TwoModel.TWO_2) {
					eatEachother();
				}
				break;
			// 快速
			case HEIGHT:
				// 单人 高手模式
				middle(snake1);
				// 蛇碰到障碍会死亡
				for (int i = 150; i <= 390; i += 30) {
					if (snake1.getHead().getX() == 150 && snake1.getHead().getY() == i) {
						snake1.isLive = false;
						GamePanel.gameStatus = GameStatus.GAMEOVER;
					}
					if (snake1.getHead().getX() == 450 && snake1.getHead().getY() == i) {
						snake1.isLive = false;
						GamePanel.gameStatus = GameStatus.GAMEOVER;
					}
				}
				break;
				default:break;
		}
	}

	// 单人 新手
	private void low() {
		for (int i = 0; i < snake1.snakeVc.size(); i++) {
			// 蛇可以穿墙
			if (snake1.snakeVc.get(i).getX() >540)
			{snake1.snakeVc.get(i).setX(30);}
			else if (snake1.snakeVc.get(i).getY() >540)
			{snake1.snakeVc.get(i).setY(30);}
			else if (snake1.snakeVc.get(i).getX() <30)
			{snake1.snakeVc.get(i).setX(540);}
			else if (snake1.snakeVc.get(i).getY() <30)
			{	snake1.snakeVc.get(i).setY(540);}
			// 判断是否吃蛇尾 蛇的节数大于5是才能吃到自己
			for (int j = 4; j < snake1.snakeVc.size(); j++) {
				if (snake1.getHead().getX() == snake1.snakeVc.get(j).getX()
						&& snake1.getHead().getY() == snake1.snakeVc.get(j).getY()) {
					snake1.isLive = false;
					GamePanel.gameStatus = GameStatus.GAMEOVER;
				}

			}
		}
	}

	// 单人 普通
	private void middle(Snake sanke) {
		// 判断是否吃蛇尾 蛇的节数大于5是才能吃到自己
		for (int j = 4; j < sanke.snakeVc.size(); j++) {
			if (sanke.getHead().getX() == sanke.snakeVc.get(j).getX()
					&& sanke.getHead().getY() == sanke.snakeVc.get(j).getY()) {
				sanke.isLive = false;
				GamePanel.gameStatus = GameStatus.GAMEOVER;
			}

		}
		// 蛇碰到墙就会死
		if (sanke.getHead().getX() >= 570) {
			sanke.isLive = false;
			GamePanel.gameStatus = GameStatus.GAMEOVER;
		} else if (sanke.getHead().getY() >= 570) {
			sanke.isLive = false;
			GamePanel.gameStatus = GameStatus.GAMEOVER;
		} else if (sanke.getHead().getX() < 30) {
			sanke.isLive = false;
			GamePanel.gameStatus = GameStatus.GAMEOVER;
		} else if (sanke.getHead().getY() < 30) {
			sanke.isLive = false;
			GamePanel.gameStatus = GameStatus.GAMEOVER;
		}
	}

	// 双人 激斗模式
	private void robFood() {
		middle(snake2);
		// 蛇2撞蛇1
		for (int i = 0; i < snake1.snakeVc.size(); i++) {
			if (snake2.getHead().getX() == snake1.snakeVc.get(i).getX()
					&& snake2.getHead().getY() == snake1.snakeVc.get(i).getY()) {
				if (i == 0) {
					snake1.isLive = false;
					snake2.isLive = false;
					GamePanel.gameStatus = GameStatus.GAMEOVER;
				} // 蛇2死
				else {
					snake2.isLive = false;
					GamePanel.gameStatus = GameStatus.GAMEOVER;
				}
			}
		}
		// 蛇1撞蛇2
		for (int i = 0; i < snake2.snakeVc.size(); i++) {
			if (snake1.getHead().getX() == snake2.snakeVc.get(i).getX()
					&& snake1.getHead().getY() == snake2.snakeVc.get(i).getY()) {
				if (i == 0) {
					snake1.isLive = false;
					snake2.isLive = false;
					GamePanel.gameStatus = GameStatus.GAMEOVER;
				} else {
					snake1.isLive = false;
					GamePanel.gameStatus = GameStatus.GAMEOVER;
				}
			}
		}
		// 生命值为0 游戏结束
		if (snake1.life == 0 || snake2.life == 0) {
			GamePanel.gameStatus = GameStatus.GAMEOVER;
		}
	}

	//限时 互吃模式
	private void eatEachother() {
		middle(snake2);
		//时间为0 游戏结束
		if(GamePanel.time==0) {GamePanel.gameStatus = GameStatus.GAMEOVER;}
		// 蛇2 吃 蛇1
		for (int i = 0; i < snake1.snakeVc.size(); i++) {
			if (snake2.getHead().getX() == snake1.snakeVc.get(i).getX()
					&& snake2.getHead().getY() == snake1.snakeVc.get(i).getY()) {
				// 撞蛇头
				if (i == 0) {
					// 吃蛇头
					if (snake1.snakeVc.size() == 1) {
						snake1.isLive = false;
						GamePanel.gameStatus = GameStatus.GAMEOVER;
					}
//					// 侧面撞蛇头 蛇第二节的X和Y都不相等为侧面
//					if (snake2.getNode2().getX() != snake1.getNode2().getX()
//							&& snake2.getNode2().getY() != snake1.getNode2().getY()) {
//						snake1.isLive = false;
//						GamePanel.gameStatus = gameStatus.GAMEOVER;
//					}
				}

				// 蛇2尾增长 蛇1减少
				else {
					snake2.addSnakenode();
					if (MySnakeGame.isHaveSound == true) {
						MySnakeGame.gameSound.musicPlay();
					}
					if (snake1.snakeVc.size() != 1) {
						snake1.removeLast();
					}
				}
			}
		}

		// 蛇1 吃 蛇2
		for (int i = 0; i < snake2.snakeVc.size(); i++) {
			if (snake1.getHead().getX() == snake2.snakeVc.get(i).getX()
					&& snake1.getHead().getY() == snake2.snakeVc.get(i).getY()) {
				// 撞蛇头
				if (i == 0) {
					// 吃蛇头
					if (snake2.snakeVc.size() == 1) {
						snake2.isLive = false;
						GamePanel.gameStatus = GameStatus.GAMEOVER;
					}
//					// 侧面撞蛇头 蛇第二节的X和Y都不相等为侧面
//					if (snake1.getNode2().getX() != snake2.getNode2().getX()
//							&& snake1.getNode2().getY() != snake2.getNode2().getY()) {
//						snake2.isLive = false;
//						GamePanel.gameStatus = gameStatus.GAMEOVER;
//						// 蛇1尾增长 蛇2减少
//					}
				} else {
					snake1.addSnakenode();
					if (MySnakeGame.isHaveSound == true) {
						MySnakeGame.gameSound.musicPlay();
					}
					if (snake2.snakeVc.size() != 1) {
						snake2.removeLast();
					}
				}
			}
		}
	}
}
