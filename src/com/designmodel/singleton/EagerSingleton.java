package com.designmodel.singleton;

/**饿汉式单例模式
 * Created by lisheng on 17-3-8.
 */
public class EagerSingleton {

    public static EagerSingleton singleton = new EagerSingleton();

    private EagerSingleton(){}

    public static EagerSingleton getInstance(){
        return singleton;
    }
}
