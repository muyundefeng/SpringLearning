package com.designmodel.test;

import com.designmodel.proxy.ProxyShopOnline;
import com.designmodel.proxy.ShopOnline;

/**客户端要针对抽象接口编程，而不是具体类
 * Created by lisheng on 17-3-16.
 */
public class ProxyTest {
    public static void main(String[] args) {
        ShopOnline shopOnline = new ProxyShopOnline();
        shopOnline.buySomethingByAgent();
    }
}
