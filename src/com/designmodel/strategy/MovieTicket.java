package com.designmodel.strategy;

/**
 * 模拟环境类，是算法对象的持有者
 * 模拟电影院打折优惠
 * 在策略模式中，对环境类和抽象策略类的理解非常重要，环境类是需要使用算法的类
 * Created by lisheng on 17-3-27.
 */
public class MovieTicket {
    private int price;
    private Discount discount;//环境类持有抽象策略类

    public MovieTicket(int price, Discount discount) {
        this.price = price;
        this.discount = discount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void getPrice() {
        discount.disount(price);
    }
}
