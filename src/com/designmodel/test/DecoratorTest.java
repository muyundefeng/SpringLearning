package com.designmodel.test;

import com.designmodel.decorator.GpsDecorator;
import com.designmodel.decorator.MusicPlay;
import com.designmodel.decorator.Phone;
import com.designmodel.decorator.SmartPhone;

/**
 * Created by lisheng on 17-3-15.
 */
public class DecoratorTest {

    public static void main(String[] args) {
        Phone phone = new SmartPhone();
        Phone addGps = new GpsDecorator(phone);
        Phone addMusic = new MusicPlay(addGps);
        addGps.callSomeone();
        System.out.println("--------");
        addMusic.callSomeone();
    }
}
