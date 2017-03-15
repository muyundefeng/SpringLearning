package com.designmodel.composite;

/**
 * Created by lisheng on 17-3-15.
 */
public interface Component {

    public void addComponent(Component component);

    public Component removeComponet();

    public void killVirtus();
}
