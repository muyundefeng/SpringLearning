package com.designmodel.decorator;

/**创建被装饰类
 * Created by lisheng on 17-3-15.
 */
public class SmartPhone implements Phone {


    @Override
    public void callSomeone() {
        System.out.println("call someone ");
    }
}
