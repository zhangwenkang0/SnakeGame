package com.snake.game;

import com.snake.enums.Direction;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
/*
 * 单节蛇类*/
public class SnakeNode {
	private int x,y;
	private Direction dir;
	public List<Point> turnXy;//存储转弯的x、y
	public List<Direction> turnDir;//存储转弯的方向
	private int size=SnakeContext.SNAKE_SIZE;

	public SnakeNode(int x,int y){
		this.x=x;
		this.y=y;
		//存储转弯的x、y
		turnXy=new ArrayList<Point>();
		//存储转弯的方向
		turnDir=new ArrayList<Direction>();
	}

	//画单节蛇
	public void draw(Graphics g){
		g.fill3DRect(x, y, size, size, false);
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public Direction getDir() {
		return dir;
	}
}
