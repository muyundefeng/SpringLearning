package com.designmodel.facade;

import com.designmodel.facade.subSystem.Tea;
import com.designmodel.facade.subSystem.TeaCup;
import com.designmodel.facade.subSystem.Water;

/**
 * 定义外观类，为客户端提供一个统一接口.
 * 可类似于SpringMVC中的dispatcherServlet的角色，
 * 接收客户端请求，然后调用子系统
 * Created by lisheng on 17-3-15.
 */
public class Facade {
    Tea tea = new Tea();
    TeaCup cup = new TeaCup();
    Water water = new Water();

    public void operation() {
        tea.getTea();
        cup.getCup();
        water.getWater();
    }
}
