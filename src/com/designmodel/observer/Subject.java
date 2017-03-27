package com.designmodel.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-3-27.
 */
public class Subject {
    public String state;
    private List<Observer> observers
            = new ArrayList<Observer>();//subject维持所有观察者对象
    public String getState() {
        return state;
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void setState(String state) {
        this.state = state;
        notifyAllObservers();

    }
    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
