package com.snake.swing;

import com.snake.deal.GamemodeDeal;
import com.snake.enums.Classes;
import com.snake.enums.GameModel;
import com.snake.enums.GameStatus;
import com.snake.enums.TwoModel;
import com.snake.extra.Music;
import com.snake.extra.Time;
import com.snake.game.Snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * SakejmGame主函数
 * @author zhangwk
 */
public class MySnakeGame extends JFrame implements ActionListener, MouseListener {
	// 定义面板
	GamePanel mp = null;
	JPanel rightPanel = null;
	public static JPanel scorePanel = null;
	JPanel statementPanel = null;
	// 菜单
	private JMenuBar jmb;
	private JMenu jmGame;
	private JMenuItem jmtStart;
	private JMenuItem jmtRestart;
	private JMenuItem jmtPause;
	private JMenuItem jmtQuit;
	// 第二列菜单 等级
	private JMenu jmMode;
	private JMenu jmOnesnake;
	private JMenuItem jmtSimple1;
	private JMenuItem jmtMiddle1;
	private JMenuItem jmtHeight1;
	private JMenu jmTwosnake;
	private JMenuItem jmtTwoMode1;
	private JMenuItem jmtTwoMode2;
	// 第三列菜单 音效
	private JMenu jmSound;
	private JMenu jmBgm;
	private JMenuItem jmtOpenbgm;
	private JMenuItem jmtClosebgm;
	private JMenu jmGameSound;
	private JMenuItem jmtOpenGamesound;
	private JMenuItem jmtCloseGamesound;
	// 第四列菜单 排行榜
	private JMenu jmRank;
	private JMenuItem jmtSimplerank;
	private JMenuItem jmtMiddlerank;
	private JMenuItem jmtHeightrank;

	// 游戏帮助
	private JMenu jmHelp;
	// 第五列菜单 关于
	private JMenu jmAbout;

	public static JLabel jlMode;// 游戏模式
	public static JLabel jlClass;// 速度级别
	public static JLabel jlScore;// 计分
	public static JLabel jlSnakeNode;// 蛇长度
	// public static JLabel JlLife;//生命
	public static JLabel jlRank;// 等级
	public static JLabel jlPlayer;// 玩家
	// 上下左右
	public static JLabel jlUp;
	public static JLabel jlDown;
	public static JLabel jlLeft;
	public static JLabel jlRight;
	// 计时 双人 模式2用到
	public static JLabel jlTime;

	// 背景音乐
	private Music bgm = null;
	public static Music gameSound = null;
	public static boolean isHaveSound;// 是否要游戏音效
	MyJDialog myJd;// 对话框

	// 线程类
	Time time60;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MySnakeGame mysnkejmGame = new MySnakeGame();
	}

	public MySnakeGame() {
		// 游戏面板
		mp = new GamePanel(this);
		this.add(mp);
		this.addKeyListener(mp);

		Snake snake = new Snake();

		// 菜单
		jmb = new JMenuBar();
		// 第一列菜单 游戏
		jmGame = new JMenu("游戏(G)");
		jmGame.setMnemonic('G');
		jmtStart = new JMenuItem("开始");
		jmtStart.addActionListener(this);
		jmtStart.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));// 设置快捷方式
		jmtRestart = new JMenuItem("重新开始");
		jmtRestart.addActionListener(this);
		jmtRestart.setAccelerator(KeyStroke.getKeyStroke('R', Event.CTRL_MASK));
		jmtPause = new JMenuItem("暂停");
		jmtPause.addActionListener(this);
		jmtPause.setAccelerator(KeyStroke.getKeyStroke('P', Event.CTRL_MASK));
		jmtQuit = new JMenuItem("退出");
		jmtQuit.addActionListener(this);
		jmtQuit.setAccelerator(KeyStroke.getKeyStroke('Q', Event.CTRL_MASK));
		// 添加
		jmGame.add(jmtStart);
		jmGame.add(jmtRestart);
		jmGame.add(jmtPause);
		jmGame.add(jmtQuit);
		// 第二列菜单 等级
		jmMode = new JMenu("游戏模式(M)");
		jmMode.setMnemonic('M');
		jmOnesnake = new JMenu("单人(O)");
		jmOnesnake.setMnemonic('O');
		jmtSimple1 = new JMenuItem("新手");
		jmtSimple1.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK));
		jmtSimple1.addActionListener(this);
		jmtMiddle1 = new JMenuItem("普通");
		jmtMiddle1.setAccelerator(KeyStroke.getKeyStroke('M', java.awt.Event.CTRL_MASK));
		jmtMiddle1.addActionListener(this);
		jmtHeight1 = new JMenuItem("高手");
		jmtHeight1.setAccelerator(KeyStroke.getKeyStroke('H', java.awt.Event.CTRL_MASK));
		jmtHeight1.addActionListener(this);

		jmTwosnake = new JMenu("双人(T)");
		jmTwosnake.setMnemonic('T');
		jmtTwoMode1 = new JMenuItem("激斗");
		jmtTwoMode1.setAccelerator(KeyStroke.getKeyStroke('S', java.awt.Event.CTRL_MASK));
		jmtTwoMode1.addActionListener(this);
		jmtTwoMode2 = new JMenuItem("来呀!互相伤害");
		jmtTwoMode2.setAccelerator(KeyStroke.getKeyStroke('M', java.awt.Event.CTRL_MASK));
		jmtTwoMode2.addActionListener(this);

		// 添加
		jmMode.add(jmOnesnake);
		jmOnesnake.add(jmtSimple1);
		jmOnesnake.add(jmtMiddle1);
		jmOnesnake.add(jmtHeight1);
		jmMode.add(jmTwosnake);
		jmTwosnake.add(jmtTwoMode1);
		jmTwosnake.add(jmtTwoMode2);

		// 第三列菜单 音效
		jmSound = new JMenu("音效(S)");
		jmSound.setMnemonic('S');
		jmBgm = new JMenu("背景音乐(B)");
		jmBgm.setMnemonic('B');
		jmtOpenbgm = new JMenuItem("开");
		jmtOpenbgm.setAccelerator(KeyStroke.getKeyStroke('O', java.awt.Event.CTRL_MASK));
		jmtOpenbgm.addActionListener(this);
		jmtClosebgm = new JMenuItem("关");
		jmtClosebgm.setAccelerator(KeyStroke.getKeyStroke('C', java.awt.Event.CTRL_MASK));
		jmtClosebgm.addActionListener(this);
		jmGameSound = new JMenu("游戏音效(M)");
		jmGameSound.setMnemonic('M');
		jmtOpenGamesound = new JMenuItem("开");
		jmtOpenGamesound.setAccelerator(KeyStroke.getKeyStroke('N', java.awt.Event.CTRL_MASK));
		jmtOpenGamesound.addActionListener(this);
		jmtCloseGamesound = new JMenuItem("关");
		jmtCloseGamesound.setAccelerator(KeyStroke.getKeyStroke('M', java.awt.Event.CTRL_MASK));
		jmtCloseGamesound.addActionListener(this);
		// 添加
		jmBgm.add(jmtOpenbgm);
		jmBgm.add(jmtClosebgm);
		jmGameSound.add(jmtOpenGamesound);
		jmGameSound.add(jmtCloseGamesound);
		jmSound.add(jmBgm);
		jmSound.add(jmGameSound);

		// 第四列菜单 排行榜
		jmRank = new JMenu("排行榜(R)");
		jmRank.setMnemonic('R');
		jmRank.addMouseListener(this);


		// 游戏帮助
		jmHelp = new JMenu("游戏帮助(H)");
		jmHelp.setMnemonic('H');
		jmHelp.addMouseListener(this);

		// 第五列菜单 关于
		jmAbout = new JMenu("关于(A)");
		jmAbout.setMnemonic('A');
		jmAbout.addMouseListener(this);

		// 添加到JMenuBar
		jmb.add(jmGame);
		jmb.add(jmMode);
		jmb.add(jmSound);
		jmb.add(jmRank);
		jmb.add(jmHelp);
		jmb.add(jmAbout);

		// 右边面板
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		this.add(rightPanel, BorderLayout.EAST);

		// 计分板
		scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(6, 1));
		jlMode = new JLabel("游戏模式:", JLabel.CENTER);
		jlMode.setFont(new Font("楷体", Font.BOLD, 20));
		jlClass = new JLabel("游戏级别:");
		jlClass.setFont(new Font("楷体", Font.PLAIN, 20));
		jlScore = new JLabel("分数:" + 0);
		jlScore.setFont(new Font("楷体", Font.PLAIN, 20));
		jlSnakeNode = new JLabel("蛇长度：3");
		jlSnakeNode.setFont(new Font("楷体", Font.PLAIN, 20));
		jlRank = new JLabel("等级:" + 0);
		jlRank.setFont(new Font("楷体", Font.PLAIN, 20));
		jlTime = new JLabel();
		jlTime.setFont(new Font("楷体", Font.BOLD, 20));
		// JlLife=new JLabel();
		// 添加
		scorePanel.add(jlMode);
		scorePanel.add(jlClass);
		scorePanel.add(jlRank);
		scorePanel.add(jlScore);
		scorePanel.add(jlSnakeNode);
		scorePanel.add(jlTime);
		// scorePanel.add(JlLife);

		rightPanel.add(scorePanel);
		// 设置边框
		scorePanel.setBorder(new LineBorder(Color.black, 1));

		// 游戏控制说明面板
		statementPanel = new JPanel();
		statementPanel.setLayout(new GridLayout(7, 1));
		JLabel jlContorl = new JLabel("游戏控制", JLabel.CENTER);
		jlContorl.setFont(new Font("楷体", Font.BOLD, 20));
		JLabel jlStart = new JLabel("开始/暂停：Space");
		jlStart.setFont(new Font("楷体", Font.PLAIN, 20));
		jlPlayer = new JLabel();
		jlPlayer.setFont(new Font("楷体", Font.PLAIN, 20));
		jlUp = new JLabel();
		jlUp.setFont(new Font("楷体", Font.PLAIN, 20));
		jlDown = new JLabel();
		jlDown.setFont(new Font("楷体", Font.PLAIN, 20));
		jlLeft = new JLabel();
		jlLeft.setFont(new Font("楷体", Font.PLAIN, 20));
		jlRight = new JLabel();
		jlRight.setFont(new Font("楷体", Font.PLAIN, 20));
		MySnakeGame.jlPlayer.setText("玩家1" + "    " + "玩家2");
		MySnakeGame.jlUp.setText("上:W" + "      " + "↑");
		MySnakeGame.jlDown.setText("下:S" + "      " + "↓");
		MySnakeGame.jlLeft.setText("左:A" + "      " + "←");
		MySnakeGame.jlRight.setText("右:D" + "      " + "→");
		statementPanel.add(jlContorl);
		statementPanel.add(jlStart);
		statementPanel.add(jlPlayer);
		statementPanel.add(jlUp);
		statementPanel.add(jlDown);
		statementPanel.add(jlLeft);
		statementPanel.add(jlRight);
		rightPanel.add(statementPanel);
		// 设置边框
		statementPanel.setBorder(new LineBorder(Color.black, 1));

		// 背景音乐
		bgm = new Music();
		bgm.musicstart("sounds/bgm.wav");
		bgm.musicLoopplay();
		// 游戏音效
		gameSound = new Music();
		gameSound.musicstart("sounds/eat.wav");
		isHaveSound = true;// 游戏音效默认为开

		// 设置JFrame
		//窗口监听
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(JOptionPane.showConfirmDialog(MySnakeGame.this, "您真的要退出吗？","确定退出",JOptionPane.OK_CANCEL_OPTION)
						==JOptionPane.OK_OPTION){
					System.exit(0);}
			}

		});
		this.setTitle("激斗贪食蛇");
		this.setIconImage(new ImageIcon("images/icon.png").getImage());
		this.setSize(768, 654);
		this.setJMenuBar(jmb);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(this); // 窗口居中
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 菜单监听
		// 开始
		if (e.getSource() == this.jmtStart) {
			if(GamePanel.gameStatus !=GameStatus.START&&GamePanel.gameStatus !=GameStatus.GAMEOVER){
				GamePanel.gameStatus = GameStatus.START;
				Thread t = new Thread(mp);
				t.start();
				if (GamePanel.gameMode == GameModel.TWO && GamePanel.twoModel == TwoModel.TWO_2) {
					Thread thread60 = new Thread(time60);
					thread60.start();
				}
			}
		}
		// 暂停
		if (e.getSource() == this.jmtPause)
			GamePanel.gameStatus = GameStatus.PAUSE;
		// 重新开始
		if (e.getSource() == this.jmtRestart)
			mp.reStart();
		// 退出游戏
		if (e.getSource() == this.jmtQuit)
			System.exit(0);
		// 单人模式
		// 新手
		if (e.getSource() == this.jmtSimple1) {
			GamePanel.gameMode = GameModel.SINGAL;
			GamePanel.classes = Classes.SIMPLE;
			GamemodeDeal.instantiate();
			mp.reStart();
		}
		// 普通
		if (e.getSource() == this.jmtMiddle1) {
			GamePanel.gameMode = GameModel.SINGAL;
			GamePanel.classes = Classes.MIDDLE;
			GamemodeDeal.instantiate();
			mp.reStart();
		}
		// 高手
		if (e.getSource() == this.jmtHeight1) {
			GamePanel.gameMode = GameModel.SINGAL;
			GamePanel.classes = Classes.HEIGHT;
			GamemodeDeal.instantiate();
			mp.reStart();
		}
		// 双人模式
		// 激斗模式
		if (e.getSource() == this.jmtTwoMode1) {
			GamePanel.gameMode = GameModel.TWO;
			GamePanel.twoModel = TwoModel.TWO_1;
			GamePanel.classes = Classes.MIDDLE;
			GamemodeDeal.instantiate();
			mp.reStart();
		}
		// 相互伤害模式
		if (e.getSource() == this.jmtTwoMode2) {
			GamePanel.gameMode = GameModel.TWO;
			GamePanel.twoModel = TwoModel.TWO_2;
			GamePanel.classes = Classes.MIDDLE;
			GamemodeDeal.instantiate();
			mp.reStart();
		}
		// 音效
		if (e.getSource() == this.jmtClosebgm) {
			bgm.musicStop();
		}
		if (e.getSource() == this.jmtOpenbgm) {
			bgm.musicLoopplay();
		}
		if (e.getSource() == this.jmtCloseGamesound) {
			isHaveSound = false;
		}
		if (e.getSource() == this.jmtOpenGamesound) {
			isHaveSound = true;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.jmRank) {
			myJd = new MyJDialog(this, "rank");
			myJd.setVisible(true);
		}
		//帮助
		if (e.getSource() == this.jmHelp) {
			myJd = new MyJDialog(this, "help");
			myJd.setVisible(true);
		}
		//关于
		if (e.getSource() == this.jmAbout) {
			myJd = new MyJDialog(this, "about");
			myJd.setVisible(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
