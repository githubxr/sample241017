package org.xq.temp.sample.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xq.temp.sample.entity.Fruit;

/**
 * 水果价格折扣配置
 * */
@Data
public class FruitConf {
    //水果名称
    private String fruitName;
    //单价
    private double priceOfHalfKg;
    //折扣率：0.9=9折
    private float discountVal=1;

    public FruitConf(String fruitName, double priceOfHalfKg){
        this.fruitName=fruitName;
        this.priceOfHalfKg=priceOfHalfKg;

    }

}
