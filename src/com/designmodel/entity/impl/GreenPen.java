package com.designmodel.entity.impl;

import com.designmodel.entity.Pen;

/**
 * Created by lisheng on 17-3-8.
 */
public class GreenPen implements Pen {
    @Override
    public void printName() {
        System.out.println("this is BluePen");
    }
}
