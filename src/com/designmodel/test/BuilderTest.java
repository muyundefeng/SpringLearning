package com.designmodel.test;

import com.designmodel.Builder.BenchBuilder;
import com.designmodel.Builder.Builder;
import com.designmodel.Builder.Director;
import com.designmodel.entity.Car;

/**
 * Created by lisheng on 17-3-9.
 */
public class BuilderTest {

    public static void main(String[] args) {
        Builder builder = new BenchBuilder();
        Director director = new Director();
        Car car = director.constructCar(builder);
        System.out.println("brand=" + car.getBrand());
        System.out.println("name=" + car.getCarName());
    }
}
