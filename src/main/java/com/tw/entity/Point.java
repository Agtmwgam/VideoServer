package com.tw.entity;

import lombok.Data;

/**
 * @Author: lushiqin
 * @Description:密度图片 热点类
 * @Date: 2019/8/9
 * @return:
 */
@Data
public class Point {
    private int x;
    private int y;
    private int weight;
    public Point(int x,int y,int weight){
        this.x=x;
        this.y=y;
        this.weight=weight;
    }

    public void toString(int x,int y,int weight){
        System.out.println(x+","+y+","+weight);
    }
}
