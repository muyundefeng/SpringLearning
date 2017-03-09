package com.designmodel.Builder;

import com.designmodel.entity.Car;

/**充当建造者，指挥者指挥建造者的组装产品的步骤
 * Created by lisheng on 17-3-9.
 */
public abstract  class Builder {
    protected Car car = new Car();

    public abstract void buildCarName();
    public abstract void buildCarType();
    public abstract void buildCarEnegine();
    public abstract void buildCarBrand();

    public Car construct(){
        return car;
    }
}
