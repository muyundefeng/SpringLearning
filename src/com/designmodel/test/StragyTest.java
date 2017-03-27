package com.designmodel.test;

import com.designmodel.strategy.KidDiscount;
import com.designmodel.strategy.MovieTicket;
import com.designmodel.strategy.StudentDiscount;
import com.designmodel.strategy.VipDiscount;

/**
 * Created by lisheng on 17-3-27.
 */
public class StragyTest {
    public static void main(String[] args) {
        MovieTicket movieTicket = new MovieTicket(30,new StudentDiscount());
        movieTicket.getPrice();
        movieTicket.setDiscount(new VipDiscount());//动态的选择策略类
        movieTicket.getPrice();
        movieTicket.setDiscount(new KidDiscount());
        movieTicket.getPrice();
    }
}
