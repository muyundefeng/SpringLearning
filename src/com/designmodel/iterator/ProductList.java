package com.designmodel.iterator;

import java.util.List;

/**
 * Created by lisheng on 17-3-27.
 */
public class ProductList extends AbstractObjectList {
    public ProductList(List products) {
        super(products);
    }
    //实现创建迭代器对象的具体工厂方法
    @Override
    public abstractIterator createAbstractInterator() {
        return new ProductIterator(this);    }
}
