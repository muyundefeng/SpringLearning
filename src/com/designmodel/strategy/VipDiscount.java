package com.designmodel.strategy;

/**
 * Created by lisheng on 17-3-27.
 */
public class VipDiscount extends Discount {

    @Override
    public void disount(int price) {
        System.out.println("vip会员打折,票价为：" + price * 0.5);
        System.out.println("VIP还可以增加积分");
        System.out.println("---------------------");
    }

}
