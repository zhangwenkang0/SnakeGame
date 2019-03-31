package com.snake.enums;

public enum TwoModel {
    /*-----------------双人模式--------------------*/
    /**
     * 双人模式1 激斗
     */
    TWO_1(1,"激斗"),
    /**
     * 双人模式2 互相伤害
     */
    TWO_2(2,"互相伤害");

    private Integer model;
    private String name;

    TwoModel(Integer model, String name){
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
