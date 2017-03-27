package com.designmodel.observer;

/**
 * Created by lisheng on 17-3-27.
 */
public class HIsOberver extends Observer {

    public HIsOberver(Subject subject){
        this.subject = subject;
        subject.attach(this);
    }
    @Override
    public void update() {
        System.out.println("his state="+subject.getState());
    }
}
