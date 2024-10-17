package org.xq.temp.sample;

import org.xq.temp.sample.conf.FruitConf;
import org.xq.temp.sample.conf.SuperMarket;
import org.xq.temp.sample.entity.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 2-面向对象思路
 * */
public class OOP {

    public static void main(String[] args) {
        //超市实例
        SuperMarket superMarket = new SuperMarket();
        //step1：添加水果配置
        Map<String, FruitConf> confMap = superMarket.getDiscountConfMap();
        String apple="apple", berry="berry", mango="mango";
        confMap.put(apple, new FruitConf(apple, 8));
        confMap.put(berry, new FruitConf(berry, 13));
        confMap.put(mango, new FruitConf(mango, 20));

        //step2：模拟四顾客购买情况
        //step2.1：A顾客
        List<Fruit> aList = new ArrayList<>();
        aList.add(new Fruit(apple, 2));
        aList.add(new Fruit(berry, 3));
        double aTotal = superMarket.checkout(aList);
        System.out.printf("A花费%f\n", aTotal);
        //模拟校验
        assert aTotal == 2*8 + 3*13;

        //step2.2：B顾客
        List<Fruit> bList = new ArrayList<>();
        bList.add(new Fruit(apple, 2));
        bList.add(new Fruit(berry, 3));
        aList.add(new Fruit(mango, 4));
        double bTotal = superMarket.checkout(bList);
        System.out.printf("B花费%f\n", bTotal);
        //模拟校验
        assert bTotal == 2*8 + 3*13 + 4*20;

        //草莓打八折促销
        confMap.get(berry).setDiscountVal(0.8f);
        //step2.3：C顾客
        List<Fruit> cList = new ArrayList<>();
        cList.add(new Fruit(apple, 2));
        cList.add(new Fruit(berry, 3));
        cList.add(new Fruit(mango, 4));

        double cTotal = superMarket.checkout(cList);
        System.out.printf("C花费%f\n", cTotal);
        //模拟校验
        assert cTotal == 2*8 + 3*13 + 4*20*0.8;

        //满100-10
        superMarket.enableDiscount(100, 10);
        //step2.4：D顾客
        List<Fruit> dList = new ArrayList<>();
        dList.add(new Fruit(apple, 2));
        dList.add(new Fruit(berry, 3));
        dList.add(new Fruit(mango, 4));

        double dTotal = superMarket.checkout(dList);
        System.out.printf("D花费%f\n", dTotal);
        //模拟校验
        double d = 2*8 + 3*13 + 4*20*0.8;
        d=d>=100?d-=10:d;
        assert dTotal == d;

    }



}
