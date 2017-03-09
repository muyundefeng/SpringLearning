package com.designmodel.Builder;

/**
 * Created by lisheng on 17-3-9.
 */
public class BWMBuilder extends Builder {
    @Override
    public void buildCarName() {
        car.setCarName("BWM-1");
    }

    @Override
    public void buildCarType() {
        car.setCarType("medium");
    }

    @Override
    public void buildCarEnegine() {
        car.setCarEnegine("oil");
    }

    @Override
    public void buildCarBrand() {
        car.setBrand("BMW");
    }
}
