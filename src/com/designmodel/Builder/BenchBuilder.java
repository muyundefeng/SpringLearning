package com.designmodel.Builder;

/**
 * Created by lisheng on 17-3-9.
 */
public class BenchBuilder extends Builder {
    @Override
    public void buildCarName() {
        car.setCarName("Bench-1");
    }

    @Override
    public void buildCarType() {
        car.setCarType("mini");
    }

    @Override
    public void buildCarEnegine() {
        car.setCarEnegine("electrict");
    }

    @Override
    public void buildCarBrand() {
        car.setBrand("bench");
    }
}
