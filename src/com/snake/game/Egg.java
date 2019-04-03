package com.snake.game;

import com.snake.enums.Classes;
import com.snake.enums.GameModel;
import com.snake.swing.GamePanel;

import java.util.Random;

public class Egg {
	public boolean isLive;// 蛋是否存活
	public  int x, y;
	private boolean isknock;// 是否与蛇位置重叠
	private Random rd;
	Snake snake1;
	Snake snake2;

	public Egg(Snake snake1, Snake snake2) {
		this.snake1 = snake1;
		this.snake2 = snake2;
		rd = new Random();
		isLive = false;
		isknock = true;
	}

	// 随机生成蛋的位置
	public void setRandom() {
		int xi = rd.nextInt(19)+1;
		int yi = rd.nextInt(19)+1;
		x = xi * 30;
		y = yi * 30;
		// 如果蛋的位置和蛇身、墙、 障碍重叠 则随机生成新位置
		while (isKnock()) {
			xi = rd.nextInt(19)+1;
			yi = rd.nextInt(19)+1;
			x = xi * 30;
			y = yi * 30;
		}
		isLive=true;

	}

	// 蛋的位置判断是否会与蛇重叠
	private boolean isKnock() {
		for (int i = 0; i < snake1.snakeNode.size(); i++) { // 单节蛇的XY
			int x0 = snake1.snakeNode.get(i).getX();
			int y0 = snake1.snakeNode.get(i).getY();
			// 重叠
			if (x + 21 > x0 && x < x0 + 30 && y + 30 > y0 && y < y0 + 30) {
				isknock = true;
				return true;
			}
		}
		if (GamePanel.gameMode == GameModel.TWO) {
			for (int i = 0; i < snake2.snakeNode.size(); i++) { // 单节蛇的XY
				int x0 = snake2.snakeNode.get(i).getX();
				int y0 = snake2.snakeNode.get(i).getY();
				// 重叠
				if (x + 21 > x0 && x < x0 + 30 && y + 30 > y0 && y < y0 + 30) {
					isknock = true;
					return true;
				}
			}
		}
		// 和墙重叠
		if (x + 21 > 570 || x < 30 || y + 30 > 570 || y < 30) {
			isknock = true;
			return true;
		}
		// 和障碍重叠
		if (GamePanel.classes == Classes.HEIGHT)
			if (x + 21 > 150 && x < 180 && y + 30 > 150 && y < 420
					|| x + 21 > 450 && x < 480 && y + 30 > 150 && y < 420) {
				isknock = true;
				return true;
			}
		// 不重叠
		isknock = false;
		return false;

	}

	// 判断蛋是否被吃了
	public boolean haveEgg() {
		for (int i = 0; i < snake1.snakeNode.size(); i++) { // 单节蛇的XY
			int x0 = snake1.snakeNode.get(i).getX();
			int y0 = snake1.snakeNode.get(i).getY();
			// 相撞 这则蛋不存活 但被吃
			if (x + 21 > x0 && x < x0 + 30 && y + 30 > y0 && y < y0 + 30) {
				isLive = false;
				snake1.isEategg=true;
				return false;
			}
		}
		if (GamePanel.gameMode == GameModel.TWO) {
			for (int i = 0; i < snake2.snakeNode.size(); i++) { // 单节蛇的XY
				int x0 = snake2.snakeNode.get(i).getX();
				int y0 = snake2.snakeNode.get(i).getY();
				// 相撞 这则蛋不存活 但被吃
				if (x + 21 > x0 && x < x0 + 30 && y + 30 > y0 && y < y0 + 30) {
					isLive = false;
					snake2.isEategg=true;
					return false;
				}

			}
		}
		// 否则蛋依然存活
		isLive = true;
		return true;

	}
}
