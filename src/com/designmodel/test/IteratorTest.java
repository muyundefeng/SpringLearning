package com.designmodel.test;

import com.designmodel.iterator.AbstractObjectList;
import com.designmodel.iterator.ProductList;
import com.designmodel.iterator.abstractIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisheng on 17-3-27.
 */
public class IteratorTest {
    public static void main(String args[]) {
        List products = new ArrayList();
        products.add("倚天剑");
        products.add("屠龙刀");
        products.add("断肠草");
        products.add("葵花宝典");
        products.add("四十二章经");

        AbstractObjectList list;
        abstractIterator iterator;

        list = new ProductList(products); //创建聚合对象
        iterator = list.createAbstractInterator();   //创建迭代器对象

        System.out.println("正向遍历：");
        while(!iterator.isLast()) {
            System.out.print(iterator.getNextItem() + "，");
            iterator.next();
        }
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("逆向遍历：");
        while(!iterator.isFirst()) {
            System.out.print(iterator.getPreviousItem() + "，");
            iterator.previous();
        }
    }
}
