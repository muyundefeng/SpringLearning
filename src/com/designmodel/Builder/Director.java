package com.designmodel.Builder;

import com.designmodel.entity.Car;

/**建造者模式中director充当指导者角色,只有该角色与客户端交互
 * Created by lisheng on 17-3-9.
 */
public class Director {
    public Car constructCar(Builder builder){
        builder.buildCarBrand();
        builder.buildCarName();
        builder.buildCarEnegine();
        builder.buildCarType();
        Car car = builder.construct();
        return car;
    }
}
