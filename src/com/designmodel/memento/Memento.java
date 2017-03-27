package com.designmodel.memento;

/**模拟备忘录模式
 *
 * 意图：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。
 主要解决：所谓备忘录模式就是在不破坏封装的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，这样可以在以后将对象恢复到原先保存的状态。
 何时使用：很多时候我们总是需要记录一个对象的内部状态，这样做的目的就是为了允许用户取消不确定或者错误的操作，能够恢复到他原先的状态，使得他有"后悔药"可吃。
 如何解决：通过一个备忘录类专门存储对象状态。
 关键代码：客户不与备忘录类耦合，与备忘录管理类耦合。
 应用实例： 1、后悔药。 2、打游戏时的存档。 3、Windows 里的 ctri + z。 4、IE 中的后退。 4、数据库的事务管理。

 在实际过程中使用一个链表或者集合保存对象状态
 在实际过程中使用一个链表或者集合保存对象状态
 * Created by lisheng on 17-3-27.
 */
public class Memento {
    private String state;

    public Memento(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
