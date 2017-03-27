package com.designmodel.strategy;

/**
 * Created by lisheng on 17-3-27.
 */
public class KidDiscount extends Discount {
    @Override
    public void disount(int price) {
        System.out.println("儿童折扣，票价为:"+(price-10));
    }
}
