package com.designmodel.flyWeight;

import java.util.Hashtable;

/**
 * 构造享元池
 * Created by lisheng on 17-3-15.
 */
public class FlyWeightFactory {
    private static FlyWeightFactory instance = new FlyWeightFactory();
    private static Hashtable ht; //使用Hashtable来存储享元对象，充当享元池

    private FlyWeightFactory() {
        ht = new Hashtable();
        BlackIgoChessMan black, white;
        black = new BlackIgoChessMan();
        ht.put("b", black);
        WhiteIgoChessMan whiteIgoChessMan = new WhiteIgoChessMan();
        ht.put("w", whiteIgoChessMan);
    }

    //返回享元工厂类的唯一实例
    public static FlyWeightFactory getInstance() {
        return instance;
    }

    //通过key来获取存储在Hashtable中的享元对象
    public static FlyWeigth getIgoChessman(String color) {
        return (FlyWeigth) ht.get(color);
    }
}
