package com.snake;

import java.applet.AudioClip; 

import java.io.*; 
import java.applet.Applet;  
import java.net.MalformedURLException; 
import java.net.URL; 


public class Music  { 
	
private  AudioClip aau; 
@SuppressWarnings("deprecation")

public  void musicstart(String musicFile){
	try { 
		 URL cb; 
		 File f = new File(musicFile); //引音乐文件
		 cb = f.toURL(); 
		 aau = Applet.newAudioClip(cb); 
       }catch (MalformedURLException e) { 
	         e.printStackTrace(); 
	 }
}
	//循环播放
	public void musicLoopplay(){
		aau.loop();
	}
	
	public void musicPlay(){
		aau.play();//播放 aau.play()				
	}
	
	public  void musicStop(){
		
		aau.stop();
	}


}
