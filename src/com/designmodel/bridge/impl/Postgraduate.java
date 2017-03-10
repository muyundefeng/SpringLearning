package com.designmodel.bridge.impl;

import com.designmodel.bridge.Student;

/**
 * Created by lisheng on 17-3-10.
 */
public class Postgraduate implements Student {
    @Override
    public String getType() {
        return "Postgraduate";
    }
}
