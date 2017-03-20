package com.designmodel.test;

import com.designmodel.chain.Chairman;
import com.designmodel.chain.Director;
import com.designmodel.chain.Handler;
import com.designmodel.chain.Manager;

/**
 * Created by lisheng on 17-3-20.
 */
public class ChainTest {
    public static void main(String[] args) {
        Handler director,manager,chairman;
        director = new Director();
        manager = new Manager();
        chairman =new Chairman();

        //创建链式请求
        director.setNextObject(manager);
        manager.setNextObject(chairman);

        //客户端分发只有第一个对象处理者交互
        director.handleRequest(500);
        director.handleRequest(1200);
        director.handleRequest(4500);
    }
}
