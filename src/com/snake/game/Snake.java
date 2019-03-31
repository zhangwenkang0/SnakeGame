package com.snake.game;

import com.snake.enums.Classes;
import com.snake.enums.Direction;
import com.snake.enums.GameModel;
import com.snake.enums.TwoModel;
import com.snake.swing.GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;
/*
 * 蛇类  用于实现蛇的移动  转弯 等*/


public class Snake {
	public  Vector<SnakeNode> snakeVc;
	private Direction dir;//方向
	public  Vector<Direction> newdirVc;//存储的新方向
	private boolean moveflag;//判断改变方向后是否移动  移动了才能再次改变方向
	public  boolean isLive;//是否存活 即游戏是否结束
	public boolean isEategg;//是否吃了蛋
	public int life;
	private boolean theSecond;//用于判断是否是蛇2
	private int speed=SnakeContext.SPEED;//一次走的格子数

	//
	public Snake(){
		snakeVc=new Vector<SnakeNode>();
		newdirVc=new Vector<Direction>();
		dir=Direction.RIGHT;
		moveflag=false;
		isLive=true;
		isEategg=false;
		life=10;
		if(GamePanel.gameMode == GameModel.SINGAL || ( GamePanel.gameMode == GameModel.TWO && GamePanel.twoModel == TwoModel.TWO_1)){
			//初始化蛇的节数
			//单人模式  或双人模式1  初始化蛇1
			for(int i=0;i<SnakeContext.SNAKE_LENGTH;i++)
			{
				SnakeNode sn;
				sn=new SnakeNode(SnakeContext.SNAKE1_X-i*SnakeContext.SNAKE_SIZE,SnakeContext.SNAKE1_Y);
				sn.setDir(this.dir);
				snakeVc.add(sn);

			}
		}
		//双人模式 模式2 初始化蛇1
		if(GamePanel.gameMode == GameModel.TWO && GamePanel.twoModel == TwoModel.TWO_2){
			for(int i=0;i<SnakeContext.SNAKE_MODE2_LENGTH;i++)
			{
				SnakeNode sn;
				sn=new SnakeNode(SnakeContext.SNAKE1_MODE2_X-i*SnakeContext.SNAKE_SIZE,SnakeContext.SNAKE1_MODE2_Y);
				sn.setDir(this.dir);
				snakeVc.add(sn);

			}
		}
	}
	//蛇2
	public Snake(boolean isSecond){
		this.theSecond=true;
		snakeVc=new Vector<SnakeNode>();
		newdirVc=new Vector<Direction>();
		dir=Direction.LEFT;
		moveflag=false;
		isLive=true;
		isEategg=false;
		life=10;
		if(GamePanel.twoModel == TwoModel.TWO_1){
			//初始化蛇的节数
			//双人模式 模式1 初始化蛇2
			for(int i=0;i<SnakeContext.SNAKE_LENGTH;i++)
			{
				SnakeNode sn;
				sn=new SnakeNode(SnakeContext.SNAKE2_X+i*SnakeContext.SNAKE_SIZE,SnakeContext.SNAKE2_Y);
				sn.setDir(this.dir);
				snakeVc.add(sn);

			}
		}
		//双人模式 模式2 初始化蛇2
		if(GamePanel.twoModel == TwoModel.TWO_2) {
			for(int i=0;i<SnakeContext.SNAKE_MODE2_LENGTH;i++)
			{
				SnakeNode sn;
				sn=new SnakeNode(SnakeContext.SNAKE2_MODE2_X+i*SnakeContext.SNAKE_SIZE,SnakeContext.SNAKE2_MODE2_Y);
				sn.setDir(this.dir);
				snakeVc.add(sn);

			}
		}
	}

	//画蛇
	public void drawSanke(Graphics g){

		for(int i=0;i<snakeVc.size();i++)
		{
			if(i==0) {
				if(!theSecond)
				{
					g.setColor(Color.red);
				}else {
					g.setColor(Color.blue);
				}
			}else {
				g.setColor(Color.green);
			}
			snakeVc.get(i).draw(g);
		}
	}


	//存储新方向 并判断是否要改变方向
	private boolean isChangeDir(Direction newdir){
		if(moveflag==true){
			if(!(newdirVc.size()==0)){

				//相反方向 不可改变
				if(!(dir==Direction.UP&&newdirVc.get(0)==Direction.DOWN||
						dir==Direction.DOWN&&newdirVc.get(0)==Direction.UP||
						dir==Direction.RIGHT&&newdirVc.get(0)==Direction.LEFT||
						dir==Direction.LEFT&&newdirVc.get(0)==Direction.RIGHT))
				{
					//令现在方向等于第一个新方向
					dir=newdirVc.get(0);
					//存储蛇头的XY  即转弯xy
					Point Xy=new Point(snakeVc.get(0).getX(),snakeVc.get(0).getY());
					//单人 新手模式  解决穿墙转弯问题
					if(GamePanel.gameMode == GameModel.TWO &&GamePanel.classes == Classes.SIMPLE){
						if(Xy.y>540) {
							Xy.y=30;
						}
						else if(Xy.y<30){
							Xy.y=540;
						}
						else if(Xy.x>540){
							Xy.x=30;
						}
						else if(Xy.x<30){
							Xy.x=540;
						}
					}

					for(int i=1;i<snakeVc.size();i++){
						//存储转弯点
						snakeVc.get(i).turnXy.add(Xy);
						//存储转弯的方向
						snakeVc.get(i).turnDir.add(dir);

					}
					return true;
				}
				else {
					newdirVc.remove(newdirVc.size()-1);
				}

			}
		}
		return false;
	}

	//改变方向
	public void changeDir(Direction newdir){
		if(isChangeDir(newdir)) {

			//改变蛇头的方向
			snakeVc.get(0).setDir(newdirVc.get(0));

			//重设置转向未移动
			moveflag=false;
			//转过后移出方向

			if(!(newdirVc.size()==0))
			{newdirVc.remove(0);}
		}
	}
	//获得蛇头
	public  SnakeNode getHead(){
		return snakeVc.get(0);
	}
	//双人  第2模式使用
	//获得第二节
	public SnakeNode getNode2(){
		return snakeVc.get(1);
	}
	//移出最后一节
	public void removeLast(){
		snakeVc.remove(snakeVc.size()-1);
	}
	//获得蛇的长度
	public int getsnakeSize(){
		return snakeVc.size();
	}

	//蛇运动
	public void Snakemove(){
		for(int i=0;i<snakeVc.size();i++){
			int x=snakeVc.get(i).getX();
			int y=snakeVc.get(i).getY();
			//处理转弯
			turn(i,x,y);

			switch(snakeVc.get(i).getDir()){
				case UP:y-=speed;SnakeNodemove(i,x,y);break;
				case DOWN:y+=speed;SnakeNodemove(i,x,y);break;
				case LEFT:x-=speed;SnakeNodemove(i,x,y);break;
				case RIGHT:x+=speed;SnakeNodemove(i,x,y);break;
			}


		}
		//设置为转完后已成功移动
		moveflag=true;

	}
	//每一节单节蛇移动

	private void SnakeNodemove(int Node,int x,int y){

		switch(snakeVc.get(Node).getDir())
		{
			case UP:

				snakeVc.get(Node).setX(x);
				snakeVc.get(Node).setY(y);

				break;
			case DOWN:
				snakeVc.get(Node).setX(x);
				snakeVc.get(Node).setY(y);

				break;
			case LEFT:

				snakeVc.get(Node).setX(x);
				snakeVc.get(Node).setY(y);

				break;
			case RIGHT:
				snakeVc.get(Node).setX(x);
				snakeVc.get(Node).setY(y);
				break;

		}

	}

	//转弯处理
	private void turn(int Node,int x,int y){
		if(!(Node==0)){

			if(!(snakeVc.get(Node).turnXy.size()==0)){
				if(x==snakeVc.get(Node).turnXy.get(0).getX()&&y==snakeVc.get(Node).turnXy.get(0).getY())
				{
					snakeVc.get(Node).setDir(snakeVc.get(Node).turnDir.get(0));

					//转过的弯移出
					snakeVc.get(Node).turnXy.remove(0);
					snakeVc.get(Node).turnDir.remove(0);

				}
			}

		}
	}

	public void addSnakenode(){
		int x = 0, y = 0;
		// 吃蛋后增加单节蛇 初始化该单节蛇的数据
		// 方向为 最后一节蛇的方向
		Direction dir = snakeVc.get(snakeVc.size() - 1).getDir();
		// 获得最后一节单节蛇的 未 转弯的方向和 未转弯的点的数量
		int dircount = snakeVc.get(snakeVc.size() - 1).turnDir.size();
		int turnxycount = snakeVc.get(snakeVc.size() - 1).turnXy.size();
		// 设定单节蛇的X、Y
		switch (dir) {
			case UP:
				x = snakeVc.get(snakeVc.size() - 1).getX();
				y = snakeVc.get(snakeVc.size() - 1).getY() + 30;
				// addDir=Direction.UP;
				break;
			case DOWN:
				x = snakeVc.get(snakeVc.size() - 1).getX();
				y = snakeVc.get(snakeVc.size() - 1).getY() - 30;
				// addDir=Direction.DOWN;
				break;
			case LEFT:
				x = snakeVc.get(snakeVc.size() - 1).getX() + 30;
				y = snakeVc.get(snakeVc.size() - 1).getY();
				// addDir=Direction.LEFT;
				break;
			case RIGHT:
				x = snakeVc.get(snakeVc.size() - 1).getX() - 30;
				y = snakeVc.get(snakeVc.size() - 1).getY();
				// addDir=Direction.RIGHT;
				break;
			default:break;
		}
		// 创建新的单节蛇
		SnakeNode snakenode = new SnakeNode(x, y);
		snakenode.setDir(dir);
		// 如果最后一节单节蛇 有未转完的 弯 则把这些值赋值到 新增的单节蛇中
		if (dircount != 0 && turnxycount != 0) {
			Vector<Direction> turnDir = new Vector<Direction>();
			for (int i = 0; i < dircount; i++) {
				turnDir.add(snakeVc.get(snakeVc.size() - 1).turnDir.get(i));
			}
			Vector<Point> turnXy = new Vector<Point>();
			for (int i = 0; i < turnxycount; i++) {
				turnXy.add(snakeVc.get(snakeVc.size() - 1).turnXy.get(i));
			}

			snakenode.turnDir = turnDir;
			snakenode.turnXy = turnXy;
		}
		snakeVc.add(snakenode);
	}

	//获得方向
	public Direction getDirection() {
		return dir;
	}

	//获得方向
	public void setDirection(Direction dir) {
		this.dir = dir;
	}

}
