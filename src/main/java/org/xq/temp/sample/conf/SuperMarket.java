package org.xq.temp.sample.conf;

import lombok.Data;
import org.xq.temp.sample.entity.Fruit;

import java.util.*;

/**
 * 超市主配置
 * */
@Data
public class SuperMarket {

    //1-超市实时折扣
    private Map<String, FruitConf> discountConfMap = new HashMap<>();


    //2-满减门槛（元）
    private int discountReq=0;
    //满减额度（元）
    private int discountVal=0;
    //是否正在满减促销
    private boolean isDiscounting=false;

    // 开启满减优惠
    public void enableDiscount(int discountReq, int discountVal) {
        this.discountReq = discountReq;  // 使用 this 来访问类成员变量
        this.discountVal = discountVal;
        this.isDiscounting = true;
    }

    //结束满减优惠
    public void disableDiscount(){
        isDiscounting = true;
    }



    // 结账
    public double checkout(List<Fruit> fruits) {
        double total = 0;
        for (Fruit fruit : fruits) {
            if (!discountConfMap.containsKey(fruit.getName())) {
                throw new RuntimeException("找不到水果：" + fruit.getName());
            }
            FruitConf conf = discountConfMap.get(fruit.getName());


            // 先计算每种水果的总价，再考虑折扣
            double weightPrice = fruit.getWeight() * conf.getPriceOfHalfKg();
            weightPrice = roundToTwoDecimals(weightPrice); // 保留两位小数

            double itemTotal = weightPrice * conf.getDiscountVal();
            itemTotal = roundToTwoDecimals(itemTotal); // 保留两位小数
            total += itemTotal;
        }

        // 计算满减
        if (this.isDiscounting && total >= this.discountReq) {
            total -= this.discountVal;
        }
        return total;
    }

    //保留两位小数
    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }


}
