package org.xq.temp.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description 水果
 * */
@Data
public class Fruit {
    //水果名字
    private String name;
    //单位：斤
    private double weight;

    public Fruit(String name, double weight){
        this.name=name;
        this.weight=weight;
    }
}
