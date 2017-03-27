package com.designmodel.memento;

import java.util.ArrayList;
import java.util.List;

/**CareTaker负责从备忘录对象中取出原先保存的状态
 * 并使用保存相关的状态
 * Created by lisheng on 17-3-27.
 */
public class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento state){
        mementoList.add(state);
    }

    public Memento get(int index){
        return mementoList.get(index);
    }
}
