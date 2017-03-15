package com.designmodel.flyWeight;

/**定义抽象享元类
 * Created by lisheng on 17-3-15.
 */
public abstract class FlyWeigth {
    //name为享元对象的内部状态
    public abstract String getName();

    public void display(Coordiate coordiate){
        System.out.println("this igo color is "+this.getName()+" location:"+coordiate.getX());
    }

}
