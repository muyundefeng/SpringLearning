package com.designmodel.singleton;

/**懒汉式单例模式,主要是为了解决多线程的单例问题,原因请参考笔记
 * Created by lisheng on 17-3-8.
 */
public class LazySingleton {

    private static LazySingleton instance = null;

    private LazySingleton() { }

    synchronized public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
