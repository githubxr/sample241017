package org.xq.temp.sample.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xq.temp.sample.conf.FruitConf;
import org.xq.temp.sample.conf.SuperMarket;
import org.xq.temp.sample.entity.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @description 测试2-OOP
 * */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = CoreSystemApplication.class)
public class OOPTest {
    //超市实例
    private SuperMarket superMarket;
    //水果配置
    private
    Map<String, FruitConf> confMap = null;

    @BeforeEach
    void setUp() {
        // step1: 添加水果配置
        superMarket = new SuperMarket();
        confMap = superMarket.getDiscountConfMap();
        String apple = "apple", berry = "berry", mango = "mango";
        confMap.put(apple, new FruitConf(apple, 8));
        confMap.put(berry, new FruitConf(berry, 13));
        confMap.put(mango, new FruitConf(mango, 20));
    }


    // 测试顾客A
    @Test
    public void testCustA() {
        List<Fruit> aList = new ArrayList<>();
        aList.add(new Fruit("apple", 2));
        aList.add(new Fruit("berry", 3));
        double total = superMarket.checkout(aList);

        // Calculate expected values with rounding and discount applied
        double expected = 0;

        // Apple
        double appleTotal = SuperMarket.roundToTwoDecimals(2 * 8);  // 2斤苹果，单价8元
        expected += appleTotal;

        // Berry
        double berryTotal = SuperMarket.roundToTwoDecimals(3 * 13);  // 3斤草莓，单价13元
        expected += berryTotal;

        // Validate total
        assertEquals(expected, total, 0.01);
    }

    // 测试顾客B
    @Test
    public void testCustB() {
        List<Fruit> bList = new ArrayList<>();
        bList.add(new Fruit("apple", 2));
        bList.add(new Fruit("berry", 3));
        bList.add(new Fruit("mango", 4));
        double total = superMarket.checkout(bList);

        // Calculate expected values
        double expected = 0;

        // Apple
        double appleTotal = SuperMarket.roundToTwoDecimals(2 * 8);  // 2斤苹果
        expected += appleTotal;

        // Berry
        double berryTotal = SuperMarket.roundToTwoDecimals(3 * 13);  // 3斤草莓
        expected += berryTotal;

        // Mango
        double mangoTotal = SuperMarket.roundToTwoDecimals(4 * 20);  // 4斤芒果
        expected += mangoTotal;

        // Validate total
        assertEquals(expected, total, 0.01);
    }

    // 测试顾客C（草莓打八折）
    @Test
    public void testCustC() {
        // Apply berry discount
        confMap.get("berry").setDiscountVal(0.8f);

        List<Fruit> cList = new ArrayList<>();
        cList.add(new Fruit("apple", 2));
        cList.add(new Fruit("berry", 3));
        cList.add(new Fruit("mango", 4));
        double total = superMarket.checkout(cList);

        // Calculate expected values
        double expected = 0;

        // Apple
        double appleTotal = SuperMarket.roundToTwoDecimals(2 * 8);
        expected += appleTotal;

        // Berry (apply discount)
        double berryTotal = SuperMarket.roundToTwoDecimals(3 * 13 * 0.8);  // 3斤草莓，8折
        expected += berryTotal;

        // Mango
        double mangoTotal = SuperMarket.roundToTwoDecimals(4 * 20);
        expected += mangoTotal;

        // Validate total
        assertEquals(expected, total, 0.01);
    }

    // 测试顾客D（草莓打八折+满减）
    @Test
    public void testCustD() {
        // Apply berry discount and enable store-wide discount
        confMap.get("berry").setDiscountVal(0.8f);
        superMarket.enableDiscount(100, 10);  // 满100减10元

        List<Fruit> dList = new ArrayList<>();
        dList.add(new Fruit("apple", 2));
        dList.add(new Fruit("berry", 3));
        dList.add(new Fruit("mango", 4));
        double total = superMarket.checkout(dList);

        // Calculate expected values
        double expected = 0;

        // Apple
        double appleTotal = SuperMarket.roundToTwoDecimals(2 * 8);
        expected += appleTotal;

        // Berry (apply discount)
        double berryTotal = SuperMarket.roundToTwoDecimals(3 * 13 * 0.8);
        expected += berryTotal;

        // Mango
        double mangoTotal = SuperMarket.roundToTwoDecimals(4 * 20);
        expected += mangoTotal;

        // Step2: Apply store-wide discount (满减)
        if (expected >= 100) {
            expected -= 10;
        }

        // Validate total
        assertEquals(expected, total, 0.01);
    }


}
