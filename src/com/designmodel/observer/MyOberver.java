package com.designmodel.observer;

/**
 * Created by lisheng on 17-3-27.
 */
public class MyOberver extends Observer {

    public MyOberver(Subject subject){
        this.subject = subject;
        subject.attach(this);//添加观察者
    }
    @Override
    public void update() {
        System.out.println("my state="+subject.getState());
    }
}
