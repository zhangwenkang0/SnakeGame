package com.snake;
//初始数据
public class SnakeContext {
	/*
	 * 每一节蛇的长度*/
	public static final int SNAKE_SIZE=30;
	/*
	 * 蛇的初始节数*/
	public static final int SNAKE_LENGTH=3;
	//双人模式 模式2
	public static final int SNAKE_MODE2_LENGTH=6;
	/*
	 *蛋的大小*/
	public static final int EGG_SIZE=30;
	/*
	 * 蛇的初始位置坐标*/ 
	//蛇1
	public static final int SNAKE1_X=90;
	public static final int SNAKE1_Y=30;
	//蛇2
	public static final int SNAKE2_X=480;
	public static final int SNAKE2_Y=540;
	
	//双人模式 模式2
	//蛇1
		public static final int SNAKE1_MODE2_X=180;
		public static final int SNAKE1_MODE2_Y=30;
		//蛇2
		public static final int SNAKE2_MODE2_X=390;
		public static final int SNAKE2_MODE2_Y=540;
	/*
	 *蛇的初始速度  每次走的30像素 */
	public static final int SPEED=30;
	 /* 刷新周期即 游戏级别 初级 中级 高级*/
	public static int CYCLE1=250;
	public static int CYCLE2=150;
	public static int CYCLE3=100;
	
}
