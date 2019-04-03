package com.snake.swing;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.snake.deal.GamemodeDeal;
import com.snake.enums.*;
import com.snake.extra.Filereadwrite;
import com.snake.extra.Time;
import com.snake.game.Snake;
import com.snake.game.SnakeContext;

/**
 * 游戏界面
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
	// 声明类
	private Snake snake1 = null;
	private Snake snake2 = null;
	private GamemodeDeal gameModedeal = null;

	public static GameModel gameMode;// 游戏模式
	public static Classes classes;// 游戏级别
	public static TwoModel twoModel;// 双人游戏模式
//	public static Classes mode;// 0位单人  1双人模式1 2 双人模式2
/*	private static String[] strGamemode = { "", "单人", "双人" };
	private static String[] strClasses = { "", "新手", "普通", "高手" };// 单人游戏级别
	private static String[] strClasses2 = { "", "激斗", "来呀!相互伤害" };// 双人游戏级别*/
	private static int score;// 分数
	private static int rank;// 等级
	private static int speed;// 蛇的运动速度 即是线程运行速度
	boolean isSpeedup;// 是否需要加速
	public static GameStatus gameStatus;// 游戏状态
	public static int time;

	MySnakeGame main;
	Time time60;

	private Thread t;

	Filereadwrite fileReadwrite;
	// 游戏级别
	public int[] classArry = { 0, SnakeContext.CYCLE1, SnakeContext.CYCLE2, SnakeContext.CYCLE3 };

	public GamePanel(MySnakeGame main) {
		this.main=main;
		// 设置游戏场地大小
		this.setSize(600, 600);
		// 初始化游戏级别为新手
		classes = Classes.SIMPLE;
		// 初始化游戏模式为单人模式
		gameMode = GameModel.SINGAL;
		// 游戏模式
		gameModedeal = new GamemodeDeal(this);
		// 实例化处理
		GamemodeDeal.instantiate();
		snake1 = GamemodeDeal.snake1;
		snake2 = GamemodeDeal.snake2;
		// 初始化速度
		speed = classArry[classes.getClasses()];
		isSpeedup = false;
		// 计分板
		// 初始化游戏计分数据
		score = 0;
		rank = 1;

		time = 60;

		// 初始化游戏状态
		gameStatus = GameStatus.NOSTART;
		//线程类
		time60 = new Time(this);

		//读取排行榜类
		fileReadwrite=new Filereadwrite();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);
		// 画墙 （普通和高手级别中）
		drawWall(g);
		// 画障碍(高手级别中)
		drawBarrier(g);
		gameModedeal.paint(g);
		// 画蛋 开始或者暂停时 蛋存在
		if (gameStatus == GameStatus.START || gameStatus == GameStatus.PAUSE || gameStatus == GameStatus.GAMEOVER) {
			//双人模式2中 不需要画蛋
			if(twoModel != TwoModel.TWO_2) {g.drawImage(new ImageIcon("images/egg.png").getImage(), GamemodeDeal.egg.x, GamemodeDeal.egg.y, this);}
		}
		//重绘
		this.repaint();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (gameStatus == GameStatus.START) {
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 计分板
			MySnakeGame.jlMode.setText("游戏模式：" + gameMode.getName());
			MySnakeGame.jlClass.setText("游戏级别:" + classes.getName());

			// snakeLength = Snake.snakeNode.size();
			// gameModedeal.threadDeal();
			gameModedeal.threadDeal();
			// 单人模式
			if (gameMode == GameModel.SINGAL) {
				MySnakeGame.jlSnakeNode.setText("蛇长度:" + GamemodeDeal.snakeLength1);
				MySnakeGame.jlSnakeNode.setFont(new Font("楷体", Font.PLAIN, 20));
				MySnakeGame.jlScore.setText("分数:" + score);
				MySnakeGame.jlScore.setFont(new Font("楷体", Font.PLAIN, 20));
				// 分数满一百分 速度加快10毫秒 最快为10毫秒
				if (score / 100 - rank == 1) {
					isSpeedup = true;
				}
				// 需要加速 则加速
				if (speed != 10) {
					if (isSpeedup) {
						speed -= 10;
						isSpeedup = false;
					}
				}
				// 分数所对应的等级
				rank = score / 100;
				MySnakeGame.jlRank.setText("等级:" + rank);

			}

			// 双人模式
			if (gameMode == GameModel.TWO) {
				MySnakeGame.jlClass.setText("模式:" + classes.getName());
				MySnakeGame.jlSnakeNode
						.setText("蛇1长度:" + GamemodeDeal.snakeLength1 + " " + "蛇2长度:" + GamemodeDeal.snakeLength2);
				MySnakeGame.jlSnakeNode.setFont(new Font("楷体", Font.PLAIN, 15));
				MySnakeGame.jlScore.setText("蛇1生命:" + GamemodeDeal.life1 + " " + "蛇2生命:" + GamemodeDeal.life2);
				MySnakeGame.jlScore.setFont(new Font("楷体", Font.PLAIN, 15));

				if (twoModel == TwoModel.TWO_2) {
					MySnakeGame.jlScore.setText("蛇1生命:" + "∞" + " " + "蛇2生命:" + "∞");

				}
			}

			// 判断是否游戏结束
			isGameover();
		}
	}

	// 吃蛋
	public void eatEgg() {
		score += 10;
		gameModedeal.eatEgg();

	}

	// 画墙
	private void drawWall(Graphics g) {
		g.setColor(new Color(245, 245, 245));
		for (int i = 0; i < 20; i++) {
			g.fill3DRect(0, i * 30, 30, 30, false);
			g.fill3DRect(i * 30, 0, 30, 30, false);
			g.fill3DRect(570, i * 30, 30, 30, false);
			g.fill3DRect(i * 30, 570, 30, 30, false);
		}
	}

	// 创建障碍物
	private void drawBarrier(Graphics g) {
		if (classes == Classes.HEIGHT) {
			for (int i = 150; i < 420; i += 90) {
				// 左障碍
				g.setColor(new Color(70, 130, 180));
				g.fill3DRect(150, i, 30, 30, false);
				g.setColor(new Color(30, 144, 255));
				g.fill3DRect(150, i + 30, 30, 30, false);
				g.setColor(new Color(135, 206, 235));
				g.fill3DRect(150, i + 60, 30, 30, false);
				// 右障碍
				g.setColor(new Color(70, 130, 180));
				g.fill3DRect(450, i, 30, 30, false);
				g.setColor(new Color(30, 144, 255));
				g.fill3DRect(450, i + 30, 30, 30, false);
				g.setColor(new Color(135, 206, 235));
				g.fill3DRect(450, i + 60, 30, 30, false);
			}
		}
	}

	// 是否死亡
	private void isGameover() {
		// 判断死亡
		if (gameStatus == GameStatus.GAMEOVER) {
			if (gameMode == GameModel.SINGAL) {
				fileReadwrite.readRank(classes.getClasses());
				if(fileReadwrite.isInRank(score)){
					String name=JOptionPane.showInputDialog(this, "请输入您的大名",
							"恭喜！您获得了第"+fileReadwrite.num+""
									+ "名");
					fileReadwrite.name[fileReadwrite.num]=name;
					fileReadwrite.writeInRank(classes.getClasses());
					MyJDialog rankDialog=new MyJDialog(main,"rank");
					rankDialog.setVisible(true);
				}
				else {JOptionPane.showMessageDialog(null, "游戏结束!您未能进入英雄榜！继续努力！");}

			}
			if (gameMode == GameModel.TWO) {
				if (twoModel == TwoModel.TWO_1) {
					if (snake1.life == 0) {
						JOptionPane.showMessageDialog(null, "玩家2胜！");
					}
					if (snake2.life == 0) {
						JOptionPane.showMessageDialog(null, "玩家1胜！");
					}
					if (snake1.isLive == false && snake2.isLive == false) {
						if (snake1.life > snake2.life) {
							JOptionPane.showMessageDialog(null, "玩家1胜！");
						} else if (snake1.life < snake2.life) {
							JOptionPane.showMessageDialog(null, "玩家2胜！");
						} else if (snake1.life == snake2.life) {
							JOptionPane.showMessageDialog(null, "同归于尽鸟！本是同根生，相煎何太急！");
						}
					}
					whoWin();
				}
				if (twoModel == TwoModel.TWO_2) {
					if (time == 0) {
						if (snake1.getsnakeSize() > snake2.getsnakeSize()) {
							JOptionPane.showMessageDialog(null, "玩家1胜！");
						} else if (snake1.getsnakeSize() < snake2.getsnakeSize()) {
							JOptionPane.showMessageDialog(null, "玩家胜！");
						} else if (snake1.getsnakeSize() == snake2.getsnakeSize()) {
							JOptionPane.showMessageDialog(null, "吃成平手！");
						}

					}
					whoWin();
				}
			}
		}
	}

	// 判断玩家输赢
	public void whoWin() {
		if (snake1.isLive == false && snake2.isLive == true) {
			JOptionPane.showMessageDialog(null, "玩家2胜！");
		} else if (snake1.isLive == true && snake2.isLive == false) {
			JOptionPane.showMessageDialog(null, "玩家1胜！");
		}
		if(twoModel == TwoModel.TWO_2){
			if(snake1.isLive == false && snake2.isLive == false){
				JOptionPane.showMessageDialog(null, "同归于尽鸟！");
			}
		}
	}

	// 重新开始游戏
	public void reStart() {
		// 初始化计分板数据
		score = 0;
		rank = 0;
		speed = classArry[classes.getClasses()];
		gameStatus = GameStatus.NOSTART;
		time = 60;

		MySnakeGame.jlScore.setText("分数:" + 0);
		MySnakeGame.jlScore.setFont(new Font("楷体", Font.PLAIN, 20));
		MySnakeGame.jlMode.setText("游戏模式：" + gameMode.getName());
		MySnakeGame.jlClass.setText("游戏级别:" + classes.getName());
		MySnakeGame.jlTime.setText("");

		// 游戏模式处理
		gameModedeal.restart();
		// 蛇长度
		MySnakeGame.jlSnakeNode.setText("蛇长度:" + GamemodeDeal.snakeLength1);
		MySnakeGame.jlSnakeNode.setFont(new Font("楷体", Font.PLAIN, 20));
		if (gameMode == GameModel.TWO) {

			MySnakeGame.jlClass.setText("模式:" + classes.getName());
			MySnakeGame.jlSnakeNode
					.setText("蛇1长度:" + GamemodeDeal.snakeLength1 + " " + "蛇2长度:" + GamemodeDeal.snakeLength2);
			MySnakeGame.jlSnakeNode.setFont(new Font("楷体", Font.PLAIN, 15));
			MySnakeGame.jlScore.setText("蛇1生命:" + GamemodeDeal.life1 + " " + "蛇2生命:" + GamemodeDeal.life2);
			MySnakeGame.jlScore.setFont(new Font("楷体", Font.PLAIN, 15));
			MySnakeGame.jlTime.setText("");
			if (twoModel == TwoModel.TWO_2) {
				MySnakeGame.jlScore.setText("蛇1生命:" + "∞" + " " + "蛇2生命:" + "∞");
				MySnakeGame.jlTime.setText("时间:" + "   " + 60);
			}

		}
		snake1 = GamemodeDeal.snake1;
		snake2 = GamemodeDeal.snake2;
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// 游戏开始时 蛇才能被控制
		if (gameStatus == GameStatus.START) {
			// 改变方向 时 将新方向添加到集合中
			if (e.getKeyCode() == KeyEvent.VK_W) {
				snake1.newdirVc.add(Direction.UP);
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				snake1.newdirVc.add(Direction.DOWN);
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				snake1.newdirVc.add(Direction.LEFT);
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				snake1.newdirVc.add(Direction.RIGHT);
			}
			if (gameMode == GameModel.TWO) {
				// 改变方向 时 将新方向添加到集合中
				if (e.getKeyCode() == KeyEvent.VK_UP) {

					snake2.newdirVc.add(Direction.UP);
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					snake2.newdirVc.add(Direction.DOWN);
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					snake2.newdirVc.add(Direction.LEFT);
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					snake2.newdirVc.add(Direction.RIGHT);
				}

			}

		}
		// 快捷
		// CTR+'+' 加速度
		if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
			if (speed != 10) {
				speed -= 10;
			}
		}
		// CTR+'-' 减速度
		if (e.getKeyCode() == KeyEvent.VK_MINUS) {
			speed += 10;
		}
		// 开始游戏 暂停游戏 Space为快捷键
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (gameStatus == GameStatus.NOSTART || gameStatus == GameStatus.PAUSE) {
				gameStatus = GameStatus.START;
				if (gameMode == GameModel.TWO && twoModel == TwoModel.TWO_2) {
					Thread thread60 = new Thread(time60);
					thread60.start();
				}
			} else if (gameStatus == GameStatus.START) {
				gameStatus = GameStatus.PAUSE;
			}
			if (gameStatus == GameStatus.START) {
				t = new Thread(this);
				t.start();
			}
		}

		// 退出游戏
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
