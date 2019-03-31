package com.snake.enums;

/**
 * 游戏模式
 */
public enum GameModel {
    /**
     * 单人模式
     */
    SINGAL(0,"单人"),
    /**
     * 双人模式
     */
    TWO(1,"双人");

    private Integer model;
    private String name;
    private GameModel (Integer model,String name){
        this.model = model;
        this.name = name;
    }

    public Integer getModel() {
        return model;
    }

    public String getName() {
        return name;
    }
}
