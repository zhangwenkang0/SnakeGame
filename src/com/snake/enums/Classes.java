package com.snake.enums;

/**
 * 游戏级别(速度)
 */
public enum Classes {
    /**
     * 初级
     */
    SIMPLE(1,"新手"),
    /**
     * 普通 (双人模式默认为普通速度)
     */
    MIDDLE(2,"普通"),
    /**
     * 高手
     */
    HEIGHT(3,"高手");

    private Integer classes;
    private String name;

     Classes(Integer classes,String name){
        this.classes = classes;
        this.name = name;
    }

    public Integer getClasses() {
        return classes;
    }

    public String getName() {
        return name;
    }
}
