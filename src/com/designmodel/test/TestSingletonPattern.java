package com.designmodel.test;

import com.designmodel.singleton.EagerSingleton;
import com.designmodel.singleton.TaskManager;
import com.designmodel.singleton.MySingleton;
/**
 * Created by lisheng on 17-3-8.
 */
public class TestSingletonPattern {
    public static void main(String[] args) {
        //为了验证单例模式，多次调用
        TaskManager taskManager1 = TaskManager.getInstance();
        TaskManager taskManager2 = TaskManager.getInstance();
        System.out.println(taskManager1 == taskManager2);

        EagerSingleton singleton = EagerSingleton.getInstance();
        EagerSingleton singleton1 = EagerSingleton.getInstance();
        System.out.println(singleton == singleton1);

        MySingleton s1, s2;
        s1 = MySingleton.getInstance();
        s2 = MySingleton.getInstance();
        System.out.println(s1==s2);

    }
}
