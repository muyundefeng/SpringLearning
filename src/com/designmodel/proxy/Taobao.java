package com.designmodel.proxy;

/**代表真是对象
 * Created by lisheng on 17-3-16.
 */
public class Taobao implements ShopOnline {
    @Override
    public void buySomethingByAgent() {
        System.out.println("buy something directly!");
    }
}
