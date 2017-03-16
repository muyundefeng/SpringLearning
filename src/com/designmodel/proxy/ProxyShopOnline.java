package com.designmodel.proxy;

/**模拟代理模式，
 * 为什么要实现接口?
 * 1.对于真实对象接口与代理对象接口对于客户端来说都必须是一样的，代理对象也必须接口。
 * 2.代理对象不仅仅要实现抽象接口定义的相关接口，还可能在真实接口调用之前或者之后增加新的操作。
 * 3.代理类要维护一个指向真实对象引用。对于真实对象来说，代理类相当于客户端。
 * Created by lisheng on 17-3-16.
 */
public class ProxyShopOnline implements ShopOnline {

    public ShopOnline shopOnline  = new Taobao() ;


    public void preBuySomethingByAgent(){
        System.out.println("need to prepare something");
    }

    @Override
    public void buySomethingByAgent() {
        this.preBuySomethingByAgent();
        shopOnline.buySomethingByAgent();
        this.afterBuySomethingByAgent();
    }

    public void afterBuySomethingByAgent(){
        System.out.println("buy something successfully");
    }
}
