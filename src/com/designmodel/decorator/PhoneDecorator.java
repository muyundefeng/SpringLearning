package com.designmodel.decorator;

/**构件抽象装饰类，需要需要在该类中存在指向抽象构件的属性
 * Created by lisheng on 17-3-15.
 */
public abstract  class PhoneDecorator implements Phone {

    public Phone phone;

    public PhoneDecorator(Phone phone){
        this.phone = phone;
    }

    @Override
    public void callSomeone() {
        phone.callSomeone();
    }
}
