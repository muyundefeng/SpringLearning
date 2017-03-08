package com.designmodel.singleton;

/**采用内部类延迟加载,推荐该方式产生单例模式
 * Created by lisheng on 17-3-8.
 */
public class MySingleton {
    private MySingleton() {
    }

    private static class HolderClass {
        private final static MySingleton instance = new MySingleton();
    }

    public static MySingleton getInstance() {
        return HolderClass.instance;
    }


}