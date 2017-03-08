package com.designmodel.test;

import com.designmodel.entity.Logger;
import com.designmodel.factory.LoggerFactoryPattern;
import com.designmodel.factory.ProductAbstractFactory;
import com.designmodel.factory.impl.FileLoggerFactory;
import com.designmodel.factory.impl.GreenProductFactoryImpl;
import com.designmodel.factory.impl.RedProductFactoryImpl;

/**
 * Created by lisheng on 17-3-8.
 */
public class TestFactoryPattern {
    public static void main(String[] args) {
        //工长方法模式测试
        LoggerFactoryPattern factoryPattern = new FileLoggerFactory();
        Logger logger = factoryPattern.createLogger();
        System.out.println(logger.getClass());

        //抽象工厂模式
        ProductAbstractFactory factory = new GreenProductFactoryImpl();
        factory.createNoteBook().printName();
        factory.createPen().printName();

        ProductAbstractFactory factory1 = new RedProductFactoryImpl();
        factory1.createNoteBook().printName();
        factory1.createPen().printName();
    }
}
