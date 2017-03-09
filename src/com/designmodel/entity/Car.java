package com.designmodel.entity;

/**建造者建造对象
 * Created by lisheng on 17-3-9.
 */
public class Car {
    public String carName;
    public String carType;
    public String carEnegine;
    public String brand;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarEnegine() {
        return carEnegine;
    }

    public void setCarEnegine(String carEnegine) {
        this.carEnegine = carEnegine;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
