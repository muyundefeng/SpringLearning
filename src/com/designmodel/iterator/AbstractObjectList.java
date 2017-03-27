package com.designmodel.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-3-27.
 */
public abstract  class AbstractObjectList {
    public List<Object> list = new ArrayList<>();

    public void addElement(Object object){
        list.add(object);
    }
    public List getObjects() {
        return this.list;
    }

    public void rmElement(Object obj){
        list.remove(obj);
    }

    public AbstractObjectList(List objets){
        list = objets;
    }
    public abstract abstractIterator createAbstractInterator();
}
