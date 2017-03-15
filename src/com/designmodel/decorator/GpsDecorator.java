package com.designmodel.decorator;

/**构件具体的装饰器类，为手机增加gps功能
 * Created by lisheng on 17-3-15.
 */
public class GpsDecorator extends PhoneDecorator {

    public GpsDecorator(Phone phone) {
        super(phone);
    }

    @Override
    public void callSomeone() {
        this.addGps();
        super.callSomeone();
    }

    //为手机增加gps功能
    public void addGps(){
        System.out.println("add gps");
    }
}
