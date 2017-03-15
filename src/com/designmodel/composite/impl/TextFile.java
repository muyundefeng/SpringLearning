package com.designmodel.composite.impl;

import com.designmodel.composite.Component;

/**
 * Created by lisheng on 17-3-15.
 */
public class TextFile implements Component {
    @Override
    public void addComponent(Component component) {
        System.out.println("not support this method");
    }

    @Override
    public Component removeComponet() {
        System.out.println("not support this method");
        return null;
    }

    @Override
    public void killVirtus() {
        System.out.println("查杀文本文件");
    }
}
