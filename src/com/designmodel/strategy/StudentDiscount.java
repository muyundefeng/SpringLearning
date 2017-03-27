package com.designmodel.strategy;

/**具体实现类，学生打折
 * Created by lisheng on 17-3-27.
 */
public class StudentDiscount extends Discount {
    @Override
    public void disount(int price) {
        System.out.println("学生优惠，票价变为：" + price * 0.8);
        System.out.println("---------------------");
    }
}
