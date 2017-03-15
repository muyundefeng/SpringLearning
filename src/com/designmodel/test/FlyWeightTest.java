package com.designmodel.test;

import com.designmodel.flyWeight.Coordiate;
import com.designmodel.flyWeight.FlyWeightFactory;
import com.designmodel.flyWeight.FlyWeigth;

/**
 * Created by lisheng on 17-3-15.
 */
public class FlyWeightTest {
    public static void main(String[] args) {
        FlyWeigth black1,black2,black3,white1,white2;
        FlyWeightFactory factory;

        //获取享元工厂对象
        factory = FlyWeightFactory.getInstance();

        //通过享元工厂获取三颗黑子
        black1 = factory.getIgoChessman("b");
        black2 = factory.getIgoChessman("b");
        black3 = factory.getIgoChessman("b");
        System.out.println("判断两颗黑子是否相同：" + (black1==black2));

        //通过享元工厂获取两颗白子
        white1 = factory.getIgoChessman("w");
        white2 = factory.getIgoChessman("w");
        System.out.println("判断两颗白子是否相同：" + (white1==white2));

        //显示棋子
        black1.display(new Coordiate(1+""));
        black2.display(new Coordiate(2+""));
        black3.display(new Coordiate(3+""));
        white1.display(new Coordiate(4+""));
        white2.display(new Coordiate(5+""));
    }
}
