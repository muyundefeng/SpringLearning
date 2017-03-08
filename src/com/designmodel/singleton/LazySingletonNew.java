package com.designmodel.singleton;

/**
 * Created by lisheng on 17-3-8.
 */
public class LazySingletonNew {

    private static LazySingletonNew instance = null;

    private LazySingletonNew() {
    }

    public static LazySingletonNew getInstance() {
        //第一重判断
        if (instance == null) {//主要是为了并发的问题，需要双重判断，当两个线程同时排队的情况，必须有内层判断
            //锁定代码块
            synchronized (LazySingletonNew.class) {
                //第二重判断
                if (instance == null) {
                    instance = new LazySingletonNew(); //创建单例实例
                }
            }
        }
        return instance;
    }
}
