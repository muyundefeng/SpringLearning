package com.designmodel.visitor;

/**访问这模式，定义抽象元素，兼职员工与全职员工的父类
 * Created by lisheng on 17-3-27.
 */
public interface Employee {
    public void accept(Department handler);//接受一个抽象访问者对该元素的访问,将访问者对象参数化传递给元素类，再具体实现子类中调用该方法
}
