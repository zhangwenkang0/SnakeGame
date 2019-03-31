package com.snake.swing;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JDialog;
import java.io.*;

/**
 * 对话框
 */
public class MyJDialog extends JDialog implements ActionListener{
	// 确定按钮
	private JButton btnOk;
	// 滑动条
	private JScrollPane jsp;
	//显示文本域
	private JTextArea jtaShow;
	private static JLabel jlSimple[];

	private static JLabel jlMiddle[];

	private static JLabel jlHeight[];

	//排行榜面板
	public JTabbedPane jtpRank;
	//新手
	private JPanel jpSimplerank;
	//普通
	public JPanel jpMiddlerank;
	//高手
	private JPanel jpHeightrank;
	

	private int kind;//级别
	
	private File file;
	private FileReader fr;
	private FileWriter fw;
	private BufferedReader cout;
	private BufferedWriter cin;

	public MyJDialog(MySnakeGame main, String str) {
		super(main, true);// 调用父类的构造函数

		this.setSize(400, 300);
		this.setLayout(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(main);

		// 创建对话框
		// 创建排行版
		if (str == "rank") {
			creatRankDialog();
			showRank("rank/simpleRank.txt",1);
			showRank("rank/middleRank.txt",2);
			showRank("rank/heightRank.txt",3);
			
		}

		// 创建帮助
		else if (str == "help") {
			creatHelpDialog();
		}
		// 创建关于
		else if (str == "about") {
			creatAboutDialog();
		}

		btnOk = new JButton("确定");
		btnOk.setMargin(new Insets(0, 0, 0, 0));
		btnOk.setBounds(170, 225, 60, 30);
		btnOk.addActionListener(this);
		this.add(btnOk);

	}

	// 创建排行榜
	private void creatRankDialog() {
		this.setTitle("排行榜");
		jtpRank=new JTabbedPane(JTabbedPane.TOP);
		jtpRank.setBounds(15, 0, 350, 225);
		jpSimplerank=new JPanel();
		jpMiddlerank=new JPanel();
		jpHeightrank=new JPanel();
		jpSimplerank.setLayout(null);
		jpMiddlerank.setLayout(null);
		jpHeightrank.setLayout(null);
		//第一二三名 JLabel
		jlSimple=new JLabel[4];
		jlMiddle=new JLabel[4];
		jlHeight=new JLabel[4];
		for(int i=1;i<4;i++){
			jlSimple[i]=new JLabel();
			jlSimple[i].setBounds(0, (i-1)*40, 350, 40);
			jlSimple[i].setFont(new Font("", Font.BOLD, 30));
			jlMiddle[i]=new JLabel();
			jlMiddle[i].setBounds(0, (i-1)*40, 350, 40);
			jlMiddle[i].setFont(new Font("", Font.BOLD, 30));
			jlHeight[i]=new JLabel();
			jlHeight[i].setBounds(0, (i-1)*40, 350, 40);
			jlHeight[i].setFont(new Font("", Font.BOLD, 30));
			jpSimplerank.add(jlSimple[i]);
			jpMiddlerank.add(jlMiddle[i]);
			jpHeightrank.add(jlHeight[i]);
		}
		
	
		
		jtpRank.add("新手排行榜", jpSimplerank);
		jtpRank.add("普通排行榜", jpMiddlerank);
		jtpRank.add("高手排行榜", jpHeightrank);
		this.add(jtpRank);

	}

	// 读取显示排行榜
	private void showRank(String filename,int kind) {
		file = new File(filename);
		String name = null;//名字
		String score=null;//分数
		String[] strRank = new String[6];
		int row = 0;// 行数
		int number = 0;// 名次
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fr = new FileReader(file);
			cout = new BufferedReader(fr);

			while (true) {
				String str = cout.readLine();

				if (str == null) {
					break;
				}
				row++;
				if (row % 2 == 0)
					{
					score = str ;
					strRank[number] = "第" + number + "名:" +name+"   "+score ;
					if(kind==1){
						jlSimple[number].setText(strRank[number]);
					}
					else if(kind==2){
						jlMiddle[number].setText(strRank[number]);
					}
					else if(kind==3){
						jlHeight[number].setText(strRank[number]);
					}
					}
				else {
					number++;
					name=str;
				}
				
			}
			cout.close();
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// System.out.println(strRank);

	}

	// 创建游戏帮助对话框
	private void creatHelpDialog() {
		this.setTitle("游戏帮助");
		jtaShow = new JTextArea("本游戏分为两种模式：单人和双人模式		  " + "单人模式：分为三种级别分别为新手、普通、高手	"
				+ "1.新手：移动速度较慢，墙壁可穿越；吃到蛇尾则死亡。				  	  " + "2.普通：移动速度比较慢，墙壁不可穿越，撞到墙或者吃到蛇尾则死亡。			  "
				+ "3.高手：移动速度快，有障碍物，墙壁，不可穿越，撞到墙，障碍物或者吃到蛇尾则死亡。		"
				+ "双人模式：分为两种模式，分别为激斗模式和限时互吃模式 ；两种模式均延续单人模式中普通级别的规则，并分别添加一下规则。     	s	"
				+ "激斗模式：初始生命值为10,玩家吃了一颗蛋，另一玩家生命值减少1，生命值为0的玩家败；玩家撞了另一玩家的尾，则该玩家败；" + "两玩家蛇头互撞，则生命值多的玩家胜，生命值相同则同归于尽。  "
				+ "限时互吃模式：60秒倒计时，玩家可以吃另一玩家的尾，以增加的长度;游戏时间结束，蛇长度长的玩家胜；" + "若一玩家只剩蛇头，被吃了蛇头后，该玩家败。		 			"
				+ "常用快捷键：Esc 退出游戏   Space 开始/暂停游戏" + "小提示:加速度 Ctrl+'+' " + "减速度 Ctrl+'-'");
		jtaShow.setFont(new Font("黑体", 0, 15));
		jtaShow.setColumns(15);
		jtaShow.setEditable(false);
		jtaShow.setLineWrap(true);// 自动换行
		jtaShow.setWrapStyleWord(true);// 高度自动增减
		jtaShow.setBounds(15, 0, 350, 225);
		jsp = new JScrollPane(jtaShow);
		jsp.setBounds(15, 0, 350, 225);
		this.add(jsp);

	}

	// 创建游戏帮助对话框
	private void creatAboutDialog() {
		this.setTitle("关于");
		jtaShow = new JTextArea("  									  "
				+ "贪食蛇1.0 	   " + "	    本游戏版权归张文康所有" + "	        开发于2016年8月");
		jtaShow.setFont(new Font("宋体", Font.BOLD, 20));
		jtaShow.setColumns(15);
		jtaShow.setEditable(false);
		jtaShow.setLineWrap(true);// 自动换行
		jtaShow.setWrapStyleWord(true);// 高度自动增减
		jtaShow.setBounds(15, 0, 350, 225);
		this.add(jtaShow);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnOk){
			this.dispose();
			}
		
	}


}
