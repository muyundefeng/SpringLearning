package com.designmodel.test;

import com.designmodel.observer.HIsOberver;
import com.designmodel.observer.MyOberver;
import com.designmodel.observer.Subject;

/**
 * Created by lisheng on 17-3-27.
 */
public class ObserverTest {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new MyOberver(subject);//增加观察者
        new HIsOberver(subject);//

        subject.setState("stage1");//更新subject对象时，更新依赖于subject对象的所有的对象
        subject.setState("stage2");
    }
}
