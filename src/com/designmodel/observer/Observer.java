package com.designmodel.observer;

/**定义抽象的观察者模式抽象类
 * 意图：定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 主要解决：一个对象状态改变给其他对象通知的问题，而且要考虑到易用和低耦合，保证高度的协作。
 何时使用：一个对象（目标对象）的状态发生改变，所有的依赖对象（观察者对象）都将得到通知，进行广播通知。
 如何解决：使用面向对象技术，可以将这种依赖关系弱化。
 关键代码：在抽象类里有一个 ArrayList 存放观察者们。
 应用实例： 1、拍卖的时候，拍卖师观察最高标价，然后通知给其他竞价者竞价。 2、西游记里面悟空请求菩萨降服红孩儿，菩萨洒了一地水招来一个老乌龟，这个乌龟就是观察者，他观察菩萨洒水这个动作。
 优点： 1、观察者和被观察者是抽象耦合的。 2、建立一套触发机制。
 Observer依赖于Subject，subject的及时更新必须能反映在observer
 * Created by lisheng on 17-3-27.
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
