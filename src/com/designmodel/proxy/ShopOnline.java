package com.designmodel.proxy;

/**非代理模式：
 * client--->realObject
 * 代理模式：
 * client--->proxyObject---->realObject
 * 在代理对象中提供一个与真实对象相同的接口，以便在任何时候都可以替代真实对象；
 *
 * 代理模式，模拟代购网站的情景
 * 定义抽象接口，真实对象与代理对象都要实现该类
 * Created by lisheng on 17-3-16.
 */
public interface ShopOnline {

    public void buySomethingByAgent();
}
